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

    void readExcel(@Param("list")List<ProjectInfo> list);

}
