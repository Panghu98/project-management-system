package group.uchain.project.VO;

import lombok.Data;

/**
 * @author panghu
 */
@Data
public class ApplyDetail {

    /**
     *项目ID
     */
    private String projectId;

    /**
     * 默认状态是 0,未审核
     * 1.审核不通过
     * 2.审核通过
     */
    private Integer approvalStatus;

    /**
     * 1.
     */
    private Integer applyType;

}
