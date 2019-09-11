package group.uchain.project.enums;

import lombok.Getter;

/**
 * @author panghu
 */

@Getter
public enum AllocationStatus {
    /**
     *
     */
    NOT_ALLOCATED(0,"未分配"),
    APPLY_TO_MODIFY(1,"申请修改中"),
    ALLOCATED(2,"已分配")
    ;

    AllocationStatus(Integer status, String tips) {
        this.status = status;
        this.tips = tips;
    }


    private Integer status;

    private String tips;

}
