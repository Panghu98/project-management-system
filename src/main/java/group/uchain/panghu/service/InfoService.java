package group.uchain.panghu.service;

import group.uchain.panghu.entity.ProjectInfo;
import group.uchain.panghu.result.Result;
import group.uchain.panghu.vo.User;

import java.util.List;
import java.util.Map;

/**
 * @author panghu
 * @title: InfoService
 * @projectName panghu
 * @date 19-7-15 上午10:14
 */
public interface InfoService {

    Result<List<ProjectInfo>> getAllProjectInfo();

    Result<List<User>> getAllUser();

    Result uploadAllocationInfo(Map<Long,Integer> list, String projectId);

    Result getAllScore();

}
