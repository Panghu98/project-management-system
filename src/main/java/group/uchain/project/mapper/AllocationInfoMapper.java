package group.uchain.project.mapper;

import group.uchain.project.vo.AllocationInfo;
import group.uchain.project.vo.AllocationInfo2;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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
     */
    void uploadAllocationInfo(@Param("map") Map<Long, BigDecimal> map, String projectId);

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


}
