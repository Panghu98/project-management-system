package group.uchain.project.service;

import group.uchain.project.entity.ApplyConfirmForm;
import group.uchain.project.entity.ApplyForm;
import group.uchain.project.result.Result;

public interface ApplyService {

    /**
     * 发起申请
     * @param applyForm 申请表单
     * @return
     */
    Result apply(ApplyForm applyForm);


    /**
     * 获取所有的申请信息
     * @return
     */
    Result getAllApplyInfo();

    /**
     * 更改申请状态
     * @param applyConfirmForm
     * @return
     */
    Result setApplyStatus(ApplyConfirmForm applyConfirmForm);


    Result getApplyDetail();
}
