package group.uchain.project.service.impl;

import group.uchain.project.dto.AllocationTempInfo;
import group.uchain.project.dto.ProjectInfo;
import group.uchain.project.entity.ApplyConfirmForm;
import group.uchain.project.entity.ApplyForm;
import group.uchain.project.enums.ApplyType;
import group.uchain.project.enums.ApprovalStatus;
import group.uchain.project.enums.CodeMsg;
import group.uchain.project.enums.ProjectStatus;
import group.uchain.project.exception.MyException;
import group.uchain.project.mapper.AllocationInfoMapper;
import group.uchain.project.mapper.ApplyInfoMapper;
import group.uchain.project.mapper.ProjectInfoMapper;
import group.uchain.project.result.Result;
import group.uchain.project.service.ApplyService;
import group.uchain.project.service.UserService;
import group.uchain.project.vo.ApplyInfo;
import group.uchain.project.vo.ApplyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author panghu
 */
@Service
public class ApplyServiceImpl implements ApplyService {


    private ProjectInfoMapper projectInfoMapper;

    private ApplyInfoMapper applyInfoMapper;

    private AllocationInfoMapper allocationInfoMapper;

    private UserService userService;

    @Autowired
    public ApplyServiceImpl(ProjectInfoMapper projectInfoMapper, ApplyInfoMapper applyInfoMapper,
                            AllocationInfoMapper allocationInfoMapper,UserService userService) {
        this.projectInfoMapper = projectInfoMapper;
        this.applyInfoMapper = applyInfoMapper;
        this.allocationInfoMapper = allocationInfoMapper;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Result apply(ApplyForm applyForm) {
        System.err.println(applyForm.toString());
        String projectId= applyForm.getProjectId();

        if (applyInfoMapper.getApplyMount(projectId) > 0) {
            return Result.error(CodeMsg.APPLY_REPEAT__ERROR);
        }
        //剩余申请次数
        Integer remainingTime = projectInfoMapper.getRemainingTime(projectId);
        if (remainingTime == 0 ){
            return Result.error(CodeMsg.APPLY_TIMES_RUN_OUT);
        }
        //更改订单状态
        projectInfoMapper.updateAllocationStatus(projectId,ProjectStatus.APPLY_FOR_MODIFYING.getStatus());
        //申请成功之后减少
        projectInfoMapper.minusRemainingTime(projectId);
        return new Result();
    }

    @Override
    public Result getAllApplyInfo() {
        List<ApplyInfo> list = applyInfoMapper.getAllApplyInfoNotApproval();
        return Result.successData(list);
    }

    @Override
    public Result setApplyStatus(ApplyConfirmForm applyConfirmForm) {
        String projectId = applyConfirmForm.getProjectId();
        int result = applyInfoMapper.updateApplyInfoStatus(applyConfirmForm);
        if (result == 0){
            throw new MyException(CodeMsg.APPLY_APPROVAL_ERROR);
        }
        Integer approvalStatus = applyConfirmForm.getApprovalStatus();
        Integer applyType = applyConfirmForm.getApplyType();


        //审核不通过,还原状态
        if (approvalStatus.equals(ApprovalStatus.NOT_APPROVAL.getApprovalStatus())){
            //如果申请的类型为延长截止时间
            if (applyType.equals(ApplyType.TIME_DELAY.getApplyType())){
                //更改项目状态
                projectInfoMapper.updateAllocationStatus(projectId,ProjectStatus.UNDISTRIBUTED.getStatus());

            //如果申请类型为申请修改项目信息
            }else {
                //更改项目状态
                projectInfoMapper.updateAllocationStatus(projectId,ProjectStatus.ALLOCATED.getStatus());
            }

        //审核通过
        }else {
            if (applyType.equals(ApplyType.UPDATE_ALLOCATION_INFO.getApplyType())){
                List<AllocationTempInfo> list = allocationInfoMapper.getAllocationTempInfoByProjectId(projectId);
                Map<Long, Double> map =new HashMap<>(32);
                for (AllocationTempInfo info:list
                ) {
                    map.put(info.getUserId(),info.getProportion());
                }
                //将原数据设置为无效
                allocationInfoMapper.updateAllocationInfoStatusByProjectId(projectId);
                //进行数据的转移
                ProjectInfo projectInfo = projectInfoMapper.getProjectInfoByProjectId(projectId);
                allocationInfoMapper.uploadAllocationInfo(map,projectId,projectInfo.getScore()/100);
                //删除临时表中的数据
                allocationInfoMapper.deleteAllocationTempInfoByProjectId(projectId);
                projectInfoMapper.updateAllocationStatus(projectId, ProjectStatus.ALLOCATED.getStatus());

            //申请类型为重新分配
            }else{
                //设置项目状态为未分配
                projectInfoMapper.updateAllocationStatus(projectId, ProjectStatus.ALLOCATED.getStatus());
            }
        }
        //删除申请信息  (这里可以通过设置状态来达到有有效性)
        applyInfoMapper.deleteApplyInfoByProjectId(projectId);
        return new Result();
    }

    @Override
    public Result getApplyMessage() {
        Long userId = userService.getCurrentUser().getUserId();
        List<ApplyMessage> list = applyInfoMapper.getApplyMessageById(userId);
        return Result.successData(list);
    }


}
