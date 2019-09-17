package group.uchain.project.form;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author panghu
 */
@Data
public class ModifyScoreForm {


    @NotNull
    private String projectId;

    @Min(0)
    @Max(500)
    private Double score;


}
