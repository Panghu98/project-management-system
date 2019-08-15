package group.uchain.project_management_system.service;

import group.uchain.project_management_system.entity.ProjectInfo;
import group.uchain.project_management_system.result.Result;
import group.uchain.project_management_system.vo.User;

import java.util.List;
import java.util.Map;

/**
 * @author project_management_system
 * @title: InfoService
 * @projectName project_management_system
 * @date 19-7-15 上午10:14
 */
public interface InfoService {

    Result<List<ProjectInfo>> getAllProjectInfo();

    Result<List<User>> getAllUser();

    Result uploadAllocationInfo(Map<Long,Integer> list, String projectId);

    Result getAllScore();

}
