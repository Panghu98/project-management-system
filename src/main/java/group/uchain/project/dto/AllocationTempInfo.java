package group.uchain.project.dto;

import lombok.Data;

import java.math.BigDecimal;

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
    private Integer projectId;

    /**
     * 成绩占比
     */
    private BigDecimal proportion;


}
