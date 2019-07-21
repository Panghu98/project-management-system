package group.uchain.panghu.service.impl;

import group.uchain.panghu.entity.ProjectInfo;
import group.uchain.panghu.enums.CodeMsg;
import group.uchain.panghu.exception.MyException;
import group.uchain.panghu.mapper.AllocationInfoMapper;
import group.uchain.panghu.mapper.ProjectInfoMapper;
import group.uchain.panghu.mapper.UserFormMapper;
import group.uchain.panghu.result.Result;
import group.uchain.panghu.service.InfoService;
import group.uchain.panghu.service.UserService;
import group.uchain.panghu.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author panghu
 * @title: InfoServiceImpkl
 * @projectName panghu
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
    public Result uploadAllocationInfo(Map<Long, Integer> map, String projectId) {
        //如果项目编号不存在的话，抛出异常  项目编号应该是不会出错的，这样写只是为了确保
        if (!projectInfoMapper.isProjectExist(projectId)){
            throw new MyException(CodeMsg.PROJECT_ID_NOI_EXIST);
        }
        allocationInfoMapper.uploadAllocationInfo(map,projectId);
        return new Result();
    }
}
