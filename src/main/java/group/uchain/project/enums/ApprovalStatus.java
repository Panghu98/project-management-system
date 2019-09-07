package group.uchain.project.enums;

import lombok.Getter;

/**
 * @author panghu
 */

@Getter
public enum  ApprovalStatus {

    /**
     * 申请确认类型
     */
    NOT_APPROVAL(0,"未审核"),
    REFUSE(1,"审批不通过"),
    AGREE(2,"审批通过");


    private Integer approvalStatus;

    private String tips;

    ApprovalStatus(Integer approvalStatus, String tips) {
        this.approvalStatus = approvalStatus;
        this.tips = tips;
    }

}
