package group.uchain.project.entity;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author panghu
 */
@Data
public class ApplyConfirmForm {

    @NotNull(message = "项目编号不为空")
    private String projectId;

    /**
     * 默认状态是 0,未审核
     * 1.审核不通过
     * 2.审核通过
     */
    @Min(1)
    @Max(2)
    @NotNull(message = "审核状态不为空")
    private Integer approvalStatus;

    /**
     * 1.延时
     * 2.重新分配
     */
    @Min(1)
    @Max(2)
    @NotNull(message = "申请状态不为空")
    private Integer applyType;

}
