package group.uchain.project_management_system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 普通用户查看的分配信息
 * @author project_management_system
 * @title: AllocationInfo
 * @projectName project_management_system
 * @date 19-7-21 下午3:30
 */
@Data
@ApiModel(value = "项目分配信息")
public class AllocationInfo {


    @ApiModelProperty(value = "项目编号")
    private String projectId;

    @ApiModelProperty(value = "成绩")
    private Double userScore;

    @ApiModelProperty(value = "项目类别")
    private String category;

    @ApiModelProperty(value = "项目负责人")
    private String leader;

    @ApiModelProperty(value = "项目说明")
    private String instruction;

}
