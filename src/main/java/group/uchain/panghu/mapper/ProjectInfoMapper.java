package group.uchain.panghu.mapper;

import group.uchain.panghu.entity.ProjectInfo;

import java.util.List;

/**
 * @author panghu
 * @title: ProjectInfoMapper
 * @projectName panghu
 * @date 19-7-13 下午9:55
 */
public interface ProjectInfoMapper {

    void readExcel(List<ProjectInfo> list);

}
