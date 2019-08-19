package group.uchain.project.service;

import group.uchain.project.dto.ProjectInfo;
import group.uchain.project.result.Result;
import group.uchain.project.vo.User;

import java.util.List;
import java.util.Map;

/**
 * @author project
 * @title: InfoService
 * @projectName project
 * @date 19-7-15 上午10:14
 */
public interface InfoService {

    Result<List<ProjectInfo>> getAllProjectInfoByUserId();

    Result<List<User>> getAllUser();

    Result uploadAllocationInfo(Map<Long,Integer> list, String projectId);

    Result getAllScore();

    Result getAllProjectInfo();
}
