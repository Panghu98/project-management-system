package group.uchain.project.mapper;

import group.uchain.project.entity.ApplyConfirmForm;
import group.uchain.project.entity.ApplyForm;
import group.uchain.project.vo.ApplyInfo;
import group.uchain.project.vo.ApplyMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplyInfoMapper {

    int addOne(ApplyForm applyForm);

    ApplyForm getApplyFormByProjectId(@Param("projectId") String projectId);

    List<ApplyInfo> getAllApplyInfoNotApproval();

    /**
     * 更新状态
     * @param applyConfirmForm 审核确认表单
     * @return 影响的条数
     */
    int updateApplyInfoStatus(ApplyConfirmForm applyConfirmForm);


    /**
     * 哦谈过用户ID获取已经审核的申请
     * @param userId 用户ID
     * @return
     */
    List<ApplyMessage> getApplyMessageById(@Param("userId") Long userId);
}
