package group.uchain.project.mapper;

import group.uchain.project.entity.ApplyConfirmForm;
import group.uchain.project.entity.ApplyForm;
import group.uchain.project.vo.ApplyInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplyInfoMapper {

    int addOne(ApplyForm applyForm);

    ApplyForm getApplyFormByProjectId(@Param("projectId") String productId);

    List<ApplyInfo> getAllApplyInfoNotApproval();

    /**
     * 更新状态
     * @param applyConfirmForm
     * @return
     */
    int updateApplyInfoStatus(ApplyConfirmForm applyConfirmForm);


}
