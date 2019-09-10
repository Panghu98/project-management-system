package group.uchain.project.util;

import group.uchain.project.DTO.OverdueProjectInfo;
import group.uchain.project.mapper.AllocationInfoMapper;
import group.uchain.project.mapper.ProjectInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author panghu
 */
@Slf4j
@Component
public class AutoAllocationTask {

    private ProjectInfoMapper projectInfoMapper;

    private AllocationInfoMapper allocationInfoMapper;

    @Autowired
    public AutoAllocationTask(ProjectInfoMapper projectInfoMapper, AllocationInfoMapper allocationInfoMapper) {
        this.projectInfoMapper = projectInfoMapper;
        this.allocationInfoMapper = allocationInfoMapper;
    }

    /**
     * 设置过期未自动分配的项目为负责人100%
     * 设置在每天晚上12.05执行
     */
    @Scheduled(cron = "0 5 0 * * ?")
    public void setOverdueProjectAllocation(){
        log.info("========================开始执行定时任务=======================");
        List<OverdueProjectInfo> list = projectInfoMapper.getAllOverdueProjectId(new Date());
        int result = allocationInfoMapper.uploadOverdueProjectAllocationInfo(list);
        log.info("========================定时任务执行完毕=======================");
        log.info("共有{}条过期未分配数据写入数据库",result);

    }



}
