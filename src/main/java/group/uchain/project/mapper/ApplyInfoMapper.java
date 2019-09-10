package group.uchain.project.mapper;

import group.uchain.project.form.ApplyConfirmForm;
import group.uchain.project.form.ApplyForm;
import group.uchain.project.VO.ApplyDetail;
import group.uchain.project.VO.ApplyInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author panghu
 */
public interface ApplyInfoMapper {

    /**
     * 通过项目编号更改申请信息的有效性
     * @param projectId 项目编号
     * @return 影响的行数
     */
    int setApplyValidStatusByProjectId(@Param("projectId") String projectId,@Param("validStatus") Integer validStatus);

    /**
     * 获取项目申请记录次数
     * @param projectId 项目编号
     * @return
     */
    int getValidApplyCount(String projectId);

    /**
     * 增加申请信息
     * @param applyForm 申请表单
     * @return
     */
    int addOne(ApplyForm applyForm);

    /**
     * 通过项目编号来获取ApplyForm
     * @param projectId  项目编号
     * @return
     */
    ApplyForm getApplyFormByProjectId(@Param("projectId") String projectId);

    /**
     * 获取所有未审核的ApplyInfo
     * @return
     */
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
    List<ApplyDetail> getApplyDetailById(@Param("userId") Long userId);

}
