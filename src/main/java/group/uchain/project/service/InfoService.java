package group.uchain.project.service;

import com.alibaba.fastjson.JSONObject;
import group.uchain.project.DTO.ProjectInfo;
import group.uchain.project.result.Result;
import group.uchain.project.VO.User;

import java.util.List;

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
     * @return
     */
    Result uploadAllocationInfo(JSONObject jsonObject);

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
    Result setDeadline(String id, Long date) throws InterruptedException;

    /**
     * 获取已经设置截止日期的项目
     * @return
     */
    Result getDeadlineProjectInfo();


}
