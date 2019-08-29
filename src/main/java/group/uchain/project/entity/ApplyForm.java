package group.uchain.project.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author panghu
 * 负责人申请修改项目分数分配
 */
@Data
public class ApplyForm {

    /**
     *项目ID
     */
    @NotNull
    private String id;

    /**
     * 申请发起人ID
     */
    @NotNull
    private Long applyUser;

}
