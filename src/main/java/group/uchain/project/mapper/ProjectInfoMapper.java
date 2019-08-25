package group.uchain.project.mapper;

import group.uchain.project.dto.ProjectInfo;
import group.uchain.project.result.Result;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author project
 * @title: ProjectInfoMapper
 * @projectName project
 * @date 19-7-13 下午9:55
 */
public interface ProjectInfoMapper {

    /**
     * 通过读取Excel注册
     * @param list
     */
    void excelToDatabase(@Param("list")List<ProjectInfo> list);

    /**
     * 获取ID重复的数量
     * @param list  传入Excel的IDList
     * @return
     */
    List<String> getRepeatNums(@Param("list")List<String> list);


    /**
     *
     */
    ProjectInfo getProjectInfoByProjectId(@Param("id") String id);
    /**
     * 负责人获取个人所拥有的项目信息
     *  已经设置了截止日期的
     * @param userId
     * @return
     */
    List<ProjectInfo> getAllProjectInfoByUserId(String userId);

    /**
     * 传入项目编号判断项目是否存在
     * @param projectId 项目编号
     * @return  时候存在
     */
    Boolean isProjectExist(@Param("id") String projectId);

    /**
     * 管理员获取所有未分配的项目信息
     * @return
     */
    List<ProjectInfo> getAllProjectInfo();

    /**
     *获取已经设置截止日期的项目信息.
     * @return
     */
    List<ProjectInfo> getDeadlineProjectInfo();

    int updateProjectInfo(ProjectInfo projectInfo);

    int deleteProjectInfo(String id);

    int setDeadline(@Param("id") String id,@Param("date") Date date);
}
