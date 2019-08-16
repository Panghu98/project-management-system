package group.uchain.project_management_system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AllocationInfo2 {//TODO

    @ApiModelProperty(value = "项目编号")
    private String projectId;

    /**
     * 类别
     */
    @ApiModelProperty(value = "项目类别")
    private String category;



}
