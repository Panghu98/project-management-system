package group.uchain.project_management_system.mapper;

import group.uchain.project_management_system.vo.AllocationInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author project_management_system
 * @title: AllocationInfoMapper
 * @projectName project_management_system
 * @date 19-7-21 上午10:58
 */
public interface AllocationInfoMapper {

    /**
     * 上传项目分配信息
     * @param map  用户ID以及对应的分配比例
     * @param projectId  项目的ID
     */
    void uploadAllocationInfo(@Param("map") Map<Long,Integer> map, String projectId);

    /**
     * 获取项目成绩分配信息
     * @param userId 用户ID
     * @return
     */
    List<AllocationInfo> getUserAllocationInfo(String userId);

}
