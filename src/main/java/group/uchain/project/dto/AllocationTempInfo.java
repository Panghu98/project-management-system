package group.uchain.project.dto;

import lombok.Data;

/**
 * @author panghu
 */
@Data
public class AllocationTempInfo {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 成绩占比
     */
    private Double proportion;


}
