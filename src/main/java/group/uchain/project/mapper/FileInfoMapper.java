package group.uchain.project.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileInfoMapper {

    /**
     * 通过项目名称获取证明材料名称
     * @param id 项目ID
     * @return 文件完整名称,包括后缀
     */
    String getCompleteFileNameByProjectId(@Param("id") String id);

    /**
     * 批量获取完整项目名称
     * @param idList 项目名称集合
     * @return 文件完整名称结合
     */
    List<String> getCompleteFileNameListByProjectId(@Param("list") List<String> idList);

}
