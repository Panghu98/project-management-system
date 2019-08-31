package group.uchain.project.enums;

import lombok.Getter;

/**
 * @author panghu
 */
@Getter
public enum ApplyType {

    /**
     * 申请类型
     */

    TIME_DELAY(1),
    UPDATE_ALLOCATION_INFO(2)
    ;

    private Integer applyType;

    ApplyType(Integer applyType) {
        this.applyType = applyType;
    }
}
