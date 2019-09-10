package group.uchain.project.form;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author panghu
 * 负责人申请修改项目分数分配
 */
@Data
public class ApplyForm implements Serializable {

    /**
     *项目ID
     */
    @NotNull(message = "项目ID不为空")
    private String projectId;

    /**
     * 申请发起人ID
     */
    @NotNull(message = "申请人不为空")
    private long applyUser;

    /**
     *申请类型 1.延时,2,重新分配
     */
    @Min(0)
    @Max(3)
    @NotNull(message = "申请类型不为空")
    private int applyType;

    @Override
    public String toString() {
        return "ApplyForm{" +
                "projectId='" + projectId + '\'' +
                ", applyUser=" + applyUser +
                ", applyType=" + applyType +
                '}';
    }
}
