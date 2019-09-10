package group.uchain.project.VO;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author panghu
 */
@Data
public class ApplyInfo {

    /**
     *项目ID
     */
    @NotNull(message = "项目ID不为空")
    private String projectId;

    /**
     * 申请发起人名字
     */
    @NotNull(message = "申请人不为空")
    private String applyUser;

    /**
     *申请类型 1.延时,2,重新分配 ,0未分配
     */
    @NotNull(message = "申请类型不为空")
    private Integer applyType;

}
