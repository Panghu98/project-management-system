package group.uchain.project.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;


/**
 * @author panghu
 */
@Data
public class Allocation {

    /**
     * 项目编号
     */
    private String projectId;

    /**
     * 划分比例以及用户ID
     */
    Map<Long, BigDecimal> map;

}
