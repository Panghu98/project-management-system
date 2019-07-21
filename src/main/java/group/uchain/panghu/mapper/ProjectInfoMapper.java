package group.uchain.panghu.mapper;

import group.uchain.panghu.entity.ProjectInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author panghu
 * @title: ProjectInfoMapper
 * @projectName panghu
 * @date 19-7-13 下午9:55
 */
public interface ProjectInfoMapper {

    /**
     * 通过读取Excel注册
     * @param list
     */
    void readExcel(@Param("list")List<ProjectInfo> list);

    /**
     * 获取ID重复的数量
     * @param list  传入Excel的IDList
     * @return
     */
    List<String> getRepeatNums(@Param("list")List<String> list);

    /**
     * 负责人获取个人所拥有的项目信息
     * @param userId
     * @return
     */
    List<ProjectInfo> getAllProjectInfo(String userId);

    /**
     * 传入项目编号判断项目是否存在
     * @param projectId 项目编号
     * @return  时候存在
     */
    Boolean isProjectExist(String projectId);

}
