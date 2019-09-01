package group.uchain.project.mapper;

import group.uchain.project.dto.AllocationTempInfo;
import group.uchain.project.vo.AllocationInfo;
import group.uchain.project.vo.AllocationInfo2;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author project
 * @title: AllocationInfoMapper
 * @projectName project
 * @date 19-7-21 上午10:58
 */
public interface AllocationInfoMapper {

    /**
     * 上传项目分配信息
     * @param map  用户ID以及对应的分配比例
     * @param projectId  项目的ID
     * @param score 项目总分数
     */
    void uploadAllocationInfo(@Param("map") Map<Long, Double> map,@Param("projectId") String projectId,@Param("score") Double score);

    /**
     * 上传项目分配信息到临时表
     * @param map  用户ID以及对应的分配比例
     * @param projectId  项目的ID
     */
    void uploadAllocationInfoToTempTable(@Param("map") Map<Long, Double> map,@Param("projectId") String projectId);


    /**
     * 上传项目分配的时间
     * @param date
     * @param projectId
     */
    void updateAllocationTime(Date date,String projectId);

    /**
     * 限于普通教师使用
     * 获取项目成绩分配信息
     * @param userId 用户ID
     * @return
     */
    List<AllocationInfo> getUserAllocationInfo(String userId);

    /**
     * 超级管理员获取所有的项目分配信息
     * @param start
     * @param end
     * @return
     */
    List<AllocationInfo2> getAllAllocationInfo(@Param("start") Date start,@Param("end") Date end);

    /**
     * 通过项目ID获取项目的信息(临时表内,用于替换)
     * @param projectId
     * @return
     */
    List<AllocationTempInfo> getAllocationTempInfoByProjectId(@Param("projectId") String projectId);

    /**
     * 删除临时申请分配项目信息
     * @param projectId 项目ID
     * @return
     */
    int deleteAllocationTempInfoByProjectId(@Param("projectId") String projectId);

    /**
     * 删除申请分配项目信息
     * @param projectId 项目ID
     * @return
     */
    int updateAllocationInfoStatusByProjectId(@Param("projectId") String projectId);

}
