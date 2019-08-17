package group.uchain.project_management_system.service.impl;

import group.uchain.project_management_system.entity.ProjectInfo;
import group.uchain.project_management_system.enums.CodeMsg;
import group.uchain.project_management_system.exception.MyException;
import group.uchain.project_management_system.mapper.AllocationInfoMapper;
import group.uchain.project_management_system.mapper.ProjectInfoMapper;
import group.uchain.project_management_system.mapper.UserFormMapper;
import group.uchain.project_management_system.result.Result;
import group.uchain.project_management_system.service.InfoService;
import group.uchain.project_management_system.service.UserService;
import group.uchain.project_management_system.vo.AllocationInfo;
import group.uchain.project_management_system.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author project_management_system
 * @title: InfoServiceImpl
 * @projectName project_management_system
 * @date 19-7-15 上午10:15
 */
@Service
public class InfoServiceImpl implements InfoService {

    private UserFormMapper userFormMapper;

    private ProjectInfoMapper projectInfoMapper;

    private UserService userService;

    private AllocationInfoMapper allocationInfoMapper;

    @Autowired
    public InfoServiceImpl(UserFormMapper userFormMapper,ProjectInfoMapper projectInfoMapper,
                           UserService userService,AllocationInfoMapper allocationInfoMapper) {
        this.userFormMapper = userFormMapper;
        this.projectInfoMapper =projectInfoMapper;
        this.userService = userService;
        this.allocationInfoMapper = allocationInfoMapper;
    }

    @Override
    public Result<List<ProjectInfo>> getAllProjectInfo() {
        String userId = String.valueOf(userService.getCurrentUser().getUserId());
        List<ProjectInfo> list = projectInfoMapper.getAllProjectInfo(userId);
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
            throw new MyException(CodeMsg.PROJECT_ID_NOI_EXIST);
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
}
