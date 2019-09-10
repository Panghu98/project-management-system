package group.uchain.project.enums;

import lombok.Getter;

/**
 * @author panghu
 */
@Getter
public enum  ApplyInfoValidStatus {
    /**
     * 申请消息的有效性
     */
    VALID(0,"有效"),
    NOT_VALID(1,"失效")
    ;


    ApplyInfoValidStatus(Integer validStatus, String tips) {
        this.validStatus = validStatus;
        this.tips = tips;
    }

    private Integer validStatus;

    private String tips;

}
