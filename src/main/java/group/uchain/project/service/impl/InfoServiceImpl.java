package group.uchain.project.service.impl;

import group.uchain.project.dto.ProjectInfo;
import group.uchain.project.enums.CodeMsg;
import group.uchain.project.exception.MyException;
import group.uchain.project.mapper.AllocationInfoMapper;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 未设置截止时间的项目
     */
    private static final String FLAG_KEY = "project-info-flag";

    /**
     * 已经设置截止时间的项目
     */
    private static final String DEADLINE_FLAG = "project-info-has-deadline-flag";

    private String hashKey = FileService.REDIS_HASH_KEY;

    /**
     * 设置了截止时间项目集合的集合名称
     */
    private String setKey = "deadline-set-key";

    @Autowired
    public InfoServiceImpl(UserFormMapper userFormMapper, ProjectInfoMapper projectInfoMapper,
                           UserService userService, AllocationInfoMapper allocationInfoMapper,
                           RedisTemplate redisTemplate) {
        this.userFormMapper = userFormMapper;
        this.projectInfoMapper =projectInfoMapper;
        this.userService = userService;
        this.allocationInfoMapper = allocationInfoMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Result<List<ProjectInfo>> getAllProjectInfoByUserId() {
        String userId = String.valueOf(userService.getCurrentUser().getUserId());
        List<ProjectInfo> list = projectInfoMapper.getAllProjectInfoByUserId(userId);
        return Result.successData(list);
    }

    @Override
    public Result<List<User>> getAllUser() {
        return Result.successData(userFormMapper.getAllUser());
    }



    @Override
    @Transactional(rollbackFor = SQLException.class)
    public Result uploadAllocationInfo(Map<Long, Integer> map, String projectId) {
        //如果项目编号不存在的话，抛出异常  项目编号应该是不会出错的，这样写只是为了确保
        if (!projectInfoMapper.isProjectExist(projectId)){
            throw new MyException(PROJECT_ID_NOI_EXIST);
        }
        allocationInfoMapper.updateAllocationTime(new Date(),projectId);
        allocationInfoMapper.uploadAllocationInfo(map,projectId);
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
        // Y有新的数据写入 ,N无新的数据写入
        //获取标记状态并且在刷新缓存之后重置状态为N
        String columnFlag = redisTemplate.opsForValue().get(FLAG_KEY).toString();
        if ("Y".equals(columnFlag)){
            log.info("数据存在更新,从数据库中读取数据");
            List<ProjectInfo> projectInfoList = projectInfoMapper.getAllProjectInfo();
            //将最新的数据放入缓存
            Map<String, Object> map = projectInfoList.stream().collect(Collectors.toMap(ProjectInfo::getId,(p)->p));
            //更新缓存 并设置过期时间为一天
            redisTemplate.opsForHash().putAll(hashKey,map);

            //更新缓存状态
            redisTemplate.opsForValue().set(FLAG_KEY,"N");
            return Result.successData(projectInfoList);
        }else {
            log.info("缓存中中为最新的键值,直接从缓存中取值");
            //获取所有的Key
            Set<Object> projectIdSet = redisTemplate.opsForHash().keys(hashKey);
            //获取所有的结果
            List list = redisTemplate.opsForHash().multiGet(hashKey,projectIdSet);
            return Result.successData(list);
        }
    }


    @Override
    public Result getDeadlineProjectInfo() {
        String flag = redisTemplate.opsForValue().get(DEADLINE_FLAG).toString();
        if ("Y".equals(flag)){
            log.info("缓存中不是最新的值,从数据库中获取数据");
            List<ProjectInfo> projectInfoList = projectInfoMapper.getDeadlineProjectInfo();
            redisTemplate.opsForSet().add(projectInfoList);
            return Result.successData(projectInfoList);
        }else {
            log.info("缓存中中为最新的键值,直接从缓存中取值");
            Set set = redisTemplate.opsForSet().members(setKey);
            return Result.successData(set);
        }
    }

    @Override
    public Result updateProjectInfo(ProjectInfo projectInfo) {
        projectInfoMapper.updateProjectInfo(projectInfo);
        return new Result();
    }

    @Override
    public Result deleteProjectInfo(String id) {
        int result = projectInfoMapper.deleteProjectInfo(id);
        if (result == 1){
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
        //项目ID为空
        if (id == null){
            return Result.error(CodeMsg.PROJECT_ID_NULL);
        }
        //日期格式错误
        if (date<System.currentTimeMillis()){
            return Result.error(CodeMsg.DATE_ERROR);
        }
        //如果项目编号不存在
        Boolean flag = projectInfoMapper.isProjectExist(id);
        if (!flag){
            return Result.error(PROJECT_ID_NOI_EXIST);
        }

        //写入内存,删除缓存  缓存和数据库同时删除,不用更新key
        projectInfoMapper.setDeadline(id,new Date(date));
        redisTemplate.opsForHash().delete(hashKey,id);

        //将设置了截止日期的项目信息放入另一个缓存当中
        ProjectInfo projectInfo = projectInfoMapper.getProjectInfoByProjectId(id);
        redisTemplate.opsForSet().add(setKey,projectInfo);
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

        //未设置截止时间的项目缓存预热操作
        List<ProjectInfo> projectInfoList = projectInfoMapper.getAllProjectInfo();
        //将最新的数据放入缓存
        Map<String, Object> map = projectInfoList.stream().collect(Collectors.toMap(ProjectInfo::getId,(p)->p));
        //更新缓存 并设置过期时间为一天
        redisTemplate.opsForHash().putAll(hashKey,map);
        //更新标志最好在最后一步进行
        redisTemplate.opsForValue().set(FLAG_KEY,"N");


        //对已经设置截止时间的项目进行预热操
        List<ProjectInfo> projectInfos = projectInfoMapper.getDeadlineProjectInfo();
        redisTemplate.opsForSet().add(setKey,projectInfos);
        redisTemplate.opsForValue().set(DEADLINE_FLAG,"N");

    }


}
