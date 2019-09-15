package group.uchain.project.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author panghu
 */
@Data
public class ApplyDetail {

    /**
     *项目ID
     */
    private String projectId;

    /**
     * 默认状态是 0,未审核
     * 1.审核不通过
     * 2.审核通过
     */
    private Integer approvalStatus;

    /**
     * 1.申请延长分配时间
     * 2.申请重新分配
     */
    private Integer applyType;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date applyTime;

}
