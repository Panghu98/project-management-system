package group.uchain.project.form;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;


/**
 * @author panghu
 */
@Data
public class AllocationForm implements Serializable{

    /**
     * 项目编号
     */
    @NotNull(message = "项目ID不为空")
    private String projectId;

    /**
     * 划分比例以及用户ID
     */
    Map<Long, Double> map;

}
