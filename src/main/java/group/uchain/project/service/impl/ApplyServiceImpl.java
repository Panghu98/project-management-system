package group.uchain.project.service.impl;

import group.uchain.project.dto.AllocationTempInfo;
import group.uchain.project.dto.ProjectInfo;
import group.uchain.project.entity.ApplyConfirmForm;
import group.uchain.project.entity.ApplyForm;
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
        int result = applyInfoMapper.addOne(applyForm);
        if (result == 0){
            //利用组合键去重
            throw new MyException(CodeMsg.APPLY_REPEAT__ERROR);
        }
        //剩余申请次数
        Integer remainingTime = projectInfoMapper.getRemainingTime(applyForm.getProjectId());
        if (remainingTime == 0 ){
            return Result.error(CodeMsg.APPLY_TIMES_RUN_OUT);
        }
        //更改订单状态
        projectInfoMapper.updateAllocationStatus(applyForm.getProjectId(), ProjectStatus.APPLY_FOR_MODIFYING.getStatus());
        //申请成功之后减少
        projectInfoMapper.minusRemainingTime(applyForm.getProjectId());
        return new Result();
    }

    @Override
    public Result getAllApplyInfo() {
        List<ApplyInfo> list = applyInfoMapper.getAllApplyInfoNotApproval();
        return Result.successData(list);
    }

    @Override
    public Result setApplyStatus(ApplyConfirmForm applyConfirmForm) {
        System.err.println(applyConfirmForm.toString());// TODO 1是不通过啊
        int result = applyInfoMapper.updateApplyInfoStatus(applyConfirmForm);
        if (result == 0){
            throw new MyException(CodeMsg.APPLY_APPROVAL_ERROR);
        }
        if (applyConfirmForm.getApprovalStatus() == 2 && applyConfirmForm.getApplyType() == 2){
            String projectId = applyConfirmForm.getProjectId();
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
            projectInfoMapper.updateAllocationStatus(applyConfirmForm.getProjectId(), ProjectStatus.ALLOCATED.getStatus());
        }
        if (applyConfirmForm.getApprovalStatus() == 2 && applyConfirmForm.getApplyType() == 1){
            //修改项目分配状态
            projectInfoMapper.updateAllocationStatus(applyConfirmForm.getProjectId(), ProjectStatus.UNDISTRIBUTED.getStatus());
        }
        return new Result();
    }

    @Override
    public Result getApplyMessage() {
        Long userId = userService.getCurrentUser().getUserId();
        List<ApplyMessage> list = applyInfoMapper.getApplyMessageById(userId);
        return Result.successData(list);
    }


}
