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

    /**
     * 获取个人所在项目的信息
     * @return
     */
    Result<List<ProjectInfo>> getAllProjectInfoByUserId();

    /**
     * 获取所有的用户
     * @return
     */
    Result<List<User>> getAllUser();

    /**
     * 上传项目分配信息
     * @param list
     * @param projectId
     * @return
     */
    Result uploadAllocationInfo(Map<Long,Integer> list, String projectId);

    /**
     * 获取项目分配的信息
     * @return
     */
    Result getAllScore();

    /**
     * 获取所有的项目信息
     * @return
     */
    Result getAllProjectInfo();

    /**
     * 修改项目信息
     * @return 是否成功
     */
    Result updateProjectInfo(ProjectInfo projectInfo);

    /**
     * 删除项目信息
     * @param id  项目ID
     * @return  是否删除成功
     */
    Result deleteProjectInfo(String id);

    /**
     * @param id  项目编号ID
     * @param date 截止日期
     * @return
     */
    Result setDeadline(String id, Long date);
}
