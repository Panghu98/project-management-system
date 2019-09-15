package group.uchain.project.service.impl;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import group.uchain.project.DTO.ProjectInfo;
import group.uchain.project.form.AllocationForm;
import group.uchain.project.enums.CodeMsg;
import group.uchain.project.enums.ProjectStatus;
import group.uchain.project.exception.MyException;
import group.uchain.project.mapper.AllocationInfoMapper;
import group.uchain.project.mapper.ApplyInfoMapper;
import group.uchain.project.mapper.ProjectInfoMapper;
import group.uchain.project.mapper.UserFormMapper;
import group.uchain.project.result.Result;
import group.uchain.project.service.FileService;
import group.uchain.project.service.InfoService;
import group.uchain.project.service.UserService;
import group.uchain.project.vo.AllocationInfo;
import group.uchain.project.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static group.uchain.project.enums.CodeMsg.*;

/**
 * @author project
 * @title: InfoServiceImpl
 * @projectName project
 * @date 19-7-15 上午10:15
 */
@Service
@Slf4j
public class InfoServiceImpl implements InfoService, InitializingBean {

    private UserFormMapper userFormMapper;

    private ProjectInfoMapper projectInfoMapper;

    private UserService userService;

    private AllocationInfoMapper allocationInfoMapper;

    private RedisTemplate redisTemplate;

    private ApplyInfoMapper applyInfoMapper;

    /**
     * 未设置截止时间的项目
     */
    private static final String FLAG_KEY = "project-info-flag";

    /**
     * 已经设置截止时间的项目
     */
    private static final String DEADLINE_FLAG = "project-info-has-deadline-flag";

    private static final String USER_REDIS_PREFIX = "user-prefix";

    private String hashKey = FileService.REDIS_HASH_KEY;

    /**
     * 设置了截止时间项目集合的集合名称
     */
    private String setKey = "deadline-set-key";

    private static final String USER_FLAG = "user-flag";


    @Autowired
    public InfoServiceImpl(UserFormMapper userFormMapper, ProjectInfoMapper projectInfoMapper,
                           UserService userService, AllocationInfoMapper allocationInfoMapper,
                           RedisTemplate redisTemplate,ApplyInfoMapper applyInfoMapper) {
        this.userFormMapper = userFormMapper;
        this.projectInfoMapper =projectInfoMapper;
        this.userService = userService;
        this.allocationInfoMapper = allocationInfoMapper;
        this.redisTemplate = redisTemplate;
        this.applyInfoMapper = applyInfoMapper;
    }

    @Override
    public Result<List<ProjectInfo>> getAllProjectInfoByUserId() {
        String userId = String.valueOf(userService.getCurrentUser().getUserId());
        List<ProjectInfo> list = projectInfoMapper.getAllProjectInfoByUserId(userId);
        return Result.successData(list);
    }

    @Override
    public Result<List<User>> getAllUser() {
        //用户需要根据中文名称进行排序,不能直接使用中文作为score
        ListOperations<String,User> listOperations = redisTemplate.opsForList();
        long ttl = redisTemplate.getExpire(USER_FLAG);
        String flag = redisTemplate.opsForValue().get(USER_FLAG).toString();
        if (ttl==-2||flag.equals("Y")){
            log.info("缓存为空或者缓存存在更新,从数据库中获取用户并放入缓存");
            List<User> list = userFormMapper.getAllUser();
            listOperations.rightPushAll(USER_REDIS_PREFIX,list);
            listOperations.getOperations().expire(USER_REDIS_PREFIX,1,TimeUnit.DAYS);
            redisTemplate.opsForValue().set(USER_FLAG,"N");
            return Result.successData(list);
        }else {
            log.info("用户信息距离过期还有{}秒",ttl);
            log.info("缓存不为空,从缓存中获取用户");
            Long end = listOperations.size(USER_REDIS_PREFIX);
            //从左到右进行读取
            List<User> userList =listOperations.range(USER_REDIS_PREFIX,0,end);
            return Result.successData(userList);
        }
    }



    @Override
    public Result uploadAllocationInfo(JSONObject jsonObject) {
        System.err.println(System.currentTimeMillis());
        String allocationString = JSONObject.toJSONString(jsonObject);
        //做JSON处理
        String rightString = allocationString.replace(",\"", ":\"")
                .replace(":\"map\":[", ",\"map\":{")
                .replace("]]","}")
                .replace("[","")
                .replace("]", "");
        JSONObject jsonResult;
        try {
            jsonResult = JSONObject.parseObject(rightString);
        }catch (JSONException exception){
            log.error(JSON_FORMAT_ERROR.getMsg());
            throw new MyException(CodeMsg.JSON_FORMAT_ERROR);
        }
        AllocationForm allocation = JSONObject.toJavaObject(jsonResult, AllocationForm.class);
        //利用Map进行去重,去除对同一个用户重复分配
        Map<Long, Double> map = allocation.getMap();
        //获取项目信息
        String projectId = allocation.getProjectId();
        ProjectInfo projectInfo = projectInfoMapper.getProjectInfoByProjectId(projectId);

        //检测用户百分比
        Long user = userService.getCurrentUser().getUserId();
        Double proportion = map.get(user);

        //多重判断  发生概率最大的放前面

        if(proportion == null){
            log.error(PROPORTION_ERROR.getMsg());
            throw new MyException(CodeMsg.PROPORTION_ERROR);
        }
        Integer minProportion = projectInfo.getDivision();
        //如果最小分配不满足
        if (minProportion > proportion.longValue()){
            return Result.error(CodeMsg.PROPORTION_MIN_ERROR);
        }

        //总和不满足总和比例
        double sum = (double) 0;
        for (Double value:map.values()){
            sum += value.longValue();
        }
        //前端传入的是数字,不是百分比,所以总和是在10000左右
        if (sum >= 100.1 || sum <= 99.9){
            log.error("分配的总和为{}",sum);
            return Result.error(CodeMsg.PROPORTION_SUM_ERROR);
        }


        if (System.currentTimeMillis() >= projectInfo.getDeadline().getTime() ){
            return Result.error(CodeMsg.PROJECT_ALLOCATION_OVERDUE);
        }

        //标记项目状态已经更新
        redisTemplate.opsForValue().set(DEADLINE_FLAG,"Y");


        Integer allocationStatus = projectInfo.getAllocationStatus();
        //如果是申请里面的,就放入临时表中
        if (applyInfoMapper.getApplyFormByProjectId(projectId) != null){
            log.info("放入临时表");
            //修改订单状态
            projectInfoMapper.updateAllocationStatus(projectId, ProjectStatus.APPLY_FOR_MODIFYING.getStatus());
            allocationInfoMapper.uploadAllocationInfoToTempTable(map,projectId,projectInfo.getScore());
            return new Result();
        }

        //检测项目是否已经分配
        if (allocationStatus == 2 ){
            return Result.error(CodeMsg.PROJECT_HAS_BEEN_ALLOCATED);
        }
        allocationInfoMapper.uploadAllocationInfo(map,projectId,projectInfo.getScore());
        //写入数据库  前端传入的是成绩整数百分比,所以要除以100
        projectInfoMapper.updateAllocationStatus(projectId, ProjectStatus.ALLOCATED.getStatus());

        //处理缓存
        ZSetOperations<String,ProjectInfo> zSetOperations = redisTemplate.opsForZSet();
        Long count;
        //同步删除  保证项目信息的一致性,删除的数据可能会和缓存中的数据不一致
        count = zSetOperations.remove(setKey,projectInfo);
        log.info("共有{}条数据从缓存集合中删除",count);
        return new Result();
    }

    @Override
    public Result getAllScore() {
        String userId = String.valueOf(userService.getCurrentUser().getUserId());
        List<AllocationInfo> list =  allocationInfoMapper.getUserAllocationInfo(userId);
        return Result.successData(list);
    }

    /**
     * 获取所有未设置截止日期的项目
     * @return
     */
    @Override
    public Result getAllProjectInfo() {
        HashOperations<String,String,ProjectInfo> hashOperations = redisTemplate.opsForHash();
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        // Y有新的数据写入 ,N无新的数据写入
        //获取标记状态并且在刷新缓存之后重置状态为N
        String columnFlag = Objects.requireNonNull(redisTemplate.opsForValue().get(FLAG_KEY)).toString();
        long ttl = redisTemplate.getExpire(hashKey);
        if ("Y".equals(columnFlag) || ttl == -2){
            log.info("数据存在更新或者过期,从数据库中读取数据");
            List<ProjectInfo> projectInfoList = projectInfoMapper.getAllProjectInfo();
            //将最新的数据放入缓存
            Map<String, ProjectInfo> map = projectInfoList.stream().collect(Collectors.toMap(ProjectInfo::getId,(p)->p));
            //更新缓存 并设置过期时间为一天
            hashOperations.putAll(hashKey,map);
            hashOperations.getOperations().expire(hashKey,1,TimeUnit.DAYS);

            //更新缓存状态
            valueOperations.set(FLAG_KEY,"N");
            return Result.successData(projectInfoList);
        }else {
            log.info("缓存中中为最新的键值,直接从缓存中取值");
            log.info("剩余的过期时间为{}秒",ttl);
            //获取所有的Key
            Set<String> projectIdSet = hashOperations.keys(hashKey);
            //获取所有的结果
            List<ProjectInfo> list = hashOperations.multiGet(hashKey,projectIdSet);
            return Result.successData(list);
        }
    }


    /**
     * 获取所有已经设置截止日期的项目
     * @return
     */
    @Override
    public Result getDeadlineProjectInfo() {
        ZSetOperations<String,ProjectInfo> zSetOperations = redisTemplate.opsForZSet();
        String flag = redisTemplate.opsForValue().get(DEADLINE_FLAG).toString();
        long ttl = redisTemplate.getExpire(setKey);
        if ("Y".equals(flag) || ttl == -2){
            log.info("缓存中不是最新的值或者缓存已过期,从数据库中获取数据");
            List<ProjectInfo> projectInfoList = projectInfoMapper.getDeadlineProjectInfo();
            for (ProjectInfo projectInfo:projectInfoList
                 ) {
                    zSetOperations.add(setKey,projectInfo,projectInfo.getAllocationStatus());
                    //设置过期时间为一天
                    zSetOperations.getOperations().expire(setKey,1,TimeUnit.DAYS);
                    redisTemplate.opsForValue().set(DEADLINE_FLAG,"N");

            }
            return Result.successData(projectInfoList);
        }else {
            log.info("缓存中中为最新的键值,直接从缓存中取值");
            log.info("剩余的过期时间为{}秒",ttl);
            Set<ProjectInfo> set = zSetOperations.rangeByScore(setKey,0,2);
            List<ProjectInfo> list = new ArrayList<>(set);
            return Result.successData(list);
        }
    }

    @Override
    public Result updateProjectInfo(ProjectInfo projectInfo) {
        projectInfoMapper.updateProjectInfo(projectInfo);
        return new Result();
    }

    @Override
    public Result deleteProjectInfo(String id) {
        HashOperations<String,String,ProjectInfo> hashOperations = redisTemplate.opsForHash();
        ZSetOperations<String,ProjectInfo> zSetOperations = redisTemplate.opsForZSet();
        int result = projectInfoMapper.deleteProjectInfo(id);
        ProjectInfo projectInfo = projectInfoMapper.getProjectInfoByProjectId(id);
        if (result >= 1){
            //删除成功,同时刷新缓存
            //未设置截止时间的项目删除占多数,所以使用缓存刷新
            Long count;
            count = hashOperations.delete(hashKey,id);
            log.info("共有{}条数据从缓存哈希中删除",count);
            //同步删除  保证项目信息的一致性,删除的数据可能会和缓存中的数据不一致
            count = zSetOperations.remove(setKey,projectInfo);
            zSetOperations.removeRange(setKey,0,2);
            log.info("共有{}条数据从缓存集合中删除",count);
            return new Result();
        }else {
            return Result.error(PROJECT_ID_NOI_EXIST);
        }
    }

    /**
     * 设置项目分配日提交时间
     * @param id  项目编号ID
     * @param date 截止日期
     * @return
     */
    @Override
    public Result setDeadline(String id, Long date) {
        HashOperations<String,String,ProjectInfo> hashOperations = redisTemplate.opsForHash();
        ZSetOperations<String,ProjectInfo> zSetOperations = redisTemplate.opsForZSet();
        //日期格式错误
        if (date<System.currentTimeMillis()){
            return Result.error(CodeMsg.DATE_ERROR);
        }
        //如果项目编号不存在
        if (projectInfoMapper.getProjectInfoByProjectId(id) == null){
            return Result.error(PROJECT_ID_NOI_EXIST);
        }

        //写入内存,删除缓存  缓存和数据库同时删除,不用更新key  到当天晚上,所以加上一天
        Date date1 = new Date(date+1000*60*60*24);
        projectInfoMapper.setDeadline(id,date1);
        hashOperations.delete(hashKey,id);

        //将设置了截止日期的项目信息放入另一个缓存当中
        ProjectInfo projectInfo = projectInfoMapper.getProjectInfoByProjectId(id);
        zSetOperations.add(setKey,projectInfo,projectInfo.getAllocationStatus());
        redisTemplate.expire(setKey,1,TimeUnit.DAYS);
        return new Result();
    }


    /**
     * 1.进行缓存预热
     * 2.在开始的时候就初始化键值,以免出现空指针异常
     */
    @Override
    public void afterPropertiesSet() {
        preheatingRedis();
    }

    /**
     * 进行缓存预热
     */
    private void preheatingRedis(){
        HashOperations<String,String,ProjectInfo> hashOperations = redisTemplate.opsForHash();
        ZSetOperations<String,ProjectInfo> zSetOperations = redisTemplate.opsForZSet();
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        //未设置截止时间的项目缓存预热操作
        List<ProjectInfo> projectInfoList = projectInfoMapper.getAllProjectInfo();
        //将最新的数据放入缓存
        Map<String, ProjectInfo> map = projectInfoList.stream().collect(Collectors.toMap(ProjectInfo::getId,(p)->p));
        //清理缓存,以免出现脏数据
        hashOperations.getOperations().delete(hashKey);
        log.info("将数据库中的数据放入缓存当中");
        //更新缓存 并设置过期时间为一天
        hashOperations.putAll(hashKey,map);
        redisTemplate.expire(hashKey,1,TimeUnit.DAYS);
        //更新标志最好在最后一步进行
        valueOperations.set(FLAG_KEY,"N");


        zSetOperations.getOperations().delete(setKey);
        //对已经设置截止时间的项目进行预热操
        List<ProjectInfo> deadlineProjectInfos = projectInfoMapper.getDeadlineProjectInfo();

        for (ProjectInfo projectInfo:deadlineProjectInfos
        ) {
            zSetOperations.add(setKey,projectInfo,projectInfo.getAllocationStatus());
        }
        zSetOperations.getOperations().expire(setKey,1,TimeUnit.DAYS);
        valueOperations.set(DEADLINE_FLAG,"N");


        valueOperations.set(USER_FLAG,"Y");

    }


}
