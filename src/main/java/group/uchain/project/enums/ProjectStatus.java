package group.uchain.project.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author panghu
 */
@Getter
@AllArgsConstructor
public enum ProjectStatus {

    /**
     * 项目分配状态
     */
    UNDISTRIBUTED(0,"未分配"),
    APPLY_FOR_MODIFYING(1,"修改申请中"),
    ALLOCATED(2,"已分配");

    private Integer status;

    private String tips;


    public Integer getStatus() {
        return status;
    }

}
