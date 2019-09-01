package group.uchain.project.service.impl;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import group.uchain.project.dto.ProjectInfo;
import group.uchain.project.entity.AllocationForm;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static group.uchain.project.enums.CodeMsg.PROJECT_ID_NOI_EXIST;

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
        Long size = listOperations.size(USER_REDIS_PREFIX);
        if (size == 0){
            log.info("缓存为空,从数据库中获取用户并放入缓存");
            List<User> list = userFormMapper.getAllUser();
            listOperations.rightPushAll(USER_REDIS_PREFIX,list);
            listOperations.getOperations().expire(USER_REDIS_PREFIX,1,TimeUnit.DAYS);
            return Result.successData(list);
        }else {
            log.info("缓存不为空,从缓存中获取用户");
            Long end = listOperations.size(USER_REDIS_PREFIX);
            //从左到右进行读取
            List<User> userList =listOperations.range(USER_REDIS_PREFIX,0,end);
            return Result.successData(userList) ;
        }
    }



    @Override
    @Transactional(rollbackFor = SQLException.class)
    public Result uploadAllocationInfo(JSONObject jsonObject) {
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
            throw new MyException(CodeMsg.JSON_FORMAT_ERROR);
        }
        AllocationForm allocation = JSONObject.toJavaObject(jsonResult, AllocationForm.class);
        //利用Map进行去重,去除对同一个用户重复分配
        Map<Long, BigDecimal> map = allocation.getMap();
        String projectId = allocation.getProjectId();
        //如果项目编号不存在的话，抛出异常
        if (projectInfoMapper.getProjectInfoByProjectId(projectId) == null){
            throw new MyException(PROJECT_ID_NOI_EXIST);
        }
        ProjectInfo projectInfo = projectInfoMapper.getProjectInfoByProjectId(projectId);
        if (projectInfo.getAllocationStatus() == 2){
            return Result.error(CodeMsg.PROJECT_HAS_BEEN_ALLOCATED);
        }
        if (System.currentTimeMillis() >= projectInfo.getDeadline().getTime() ){
            return Result.error(CodeMsg.PROJECT_ALLOCATION_OVERDUE);
        }

        //如果是申请里面的,就放入临时表中
        if (applyInfoMapper.getApplyFormByProjectId(projectId) != null){
            allocationInfoMapper.uploadAllocationInfoToTempTable(map,projectId);
        }
        allocationInfoMapper.uploadAllocationInfo(map,projectId,projectInfo.getScore()/100);
        //写入数据库  前端传入的是成绩整数百分比,所以要除以100
        projectInfoMapper.updateAllocationStatus(projectId, ProjectStatus.ALLOCATED.getStatus());
        return new Result();
    }

    @Override
    public Result getAllScore() {
        String userId = String.valueOf(userService.getCurrentUser().getUserId());
        List<AllocationInfo> list =  allocationInfoMapper.getUserAllocationInfo(userId);
        return Result.successData(list);
    }

    @Override
    public Result getAllProjectInfo() {
        HashOperations<String,String,ProjectInfo> hashOperations = redisTemplate.opsForHash();
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        // Y有新的数据写入 ,N无新的数据写入
        //获取标记状态并且在刷新缓存之后重置状态为N
        String columnFlag = Objects.requireNonNull(redisTemplate.opsForValue().get(FLAG_KEY)).toString();
        if ("Y".equals(columnFlag) || hashOperations.keys(hashKey).size() == 0){
            log.info("数据存在更新,从数据库中读取数据");
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
            //获取所有的Key
            Set<String> projectIdSet = hashOperations.keys(hashKey);
            //获取所有的结果
            List list = hashOperations.multiGet(hashKey,projectIdSet);
            return Result.successData(list);
        }
    }


    @Override
    public Result getDeadlineProjectInfo() {
        ZSetOperations<String,ProjectInfo> zSetOperations = redisTemplate.opsForZSet();
        String flag = redisTemplate.opsForValue().get(DEADLINE_FLAG).toString();
        if ("Y".equals(flag) || zSetOperations.size(setKey) == 0){
            log.info("缓存中不是最新的值,从数据库中获取数据");
            List<ProjectInfo> projectInfoList = projectInfoMapper.getDeadlineProjectInfo();
            for (ProjectInfo projectInfo:projectInfoList
                 ) {
                    zSetOperations.add(setKey,projectInfo,projectInfo.getAllocationStatus());
                    //设置过期时间为一天
                    zSetOperations.getOperations().expire(setKey,1,TimeUnit.DAYS);
            }
            return Result.successData(projectInfoList);
        }else {
            log.info("缓存中中为最新的键值,直接从缓存中取值");
            Set set = zSetOperations.rangeByScore(setKey,0,1);
            List list = new ArrayList(set);
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
        int result = projectInfoMapper.deleteProjectInfo(id);
        System.out.println(result);
        if (result >= 1){
            //删除成功,同时刷新缓存
            //未设置截止时间的项目删除占多数,所以使用缓存刷新
            hashOperations.delete(hashKey,id);
            //设置了截止时间的项目占极少数,使用数据库刷新
            redisTemplate.opsForValue().set(DEADLINE_FLAG,"Y");
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
        System.err.println(projectInfoList);
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

    }


}
