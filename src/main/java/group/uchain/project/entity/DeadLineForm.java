package group.uchain.project.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author panghu
 */
@Data
public class DeadLineForm {

    @NotNull(message = "项目编号不能为空")
    private String id;

    @NotNull(message = "截止时间不能为空")
    private Long date;



}
