package group.uchain.project.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 管理员查看的项目分配信息
 * @author panghu
 */
@Data
public class AllocationInfo2 {

    @ApiModelProperty(value = "项目编号")
    private String projectId;

    @ApiModelProperty(value = "项目类别")
    private String category;

    @ApiModelProperty(value = "项目负责人")
    private String leader;

    @ApiModelProperty(value = "项目说明")
    private String instruction;

    @ApiModelProperty(value = "项目分配的成员信息")
    private List<Teacher> teachers;


}
