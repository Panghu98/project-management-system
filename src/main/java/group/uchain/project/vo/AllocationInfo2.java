package group.uchain.project.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 管理员查看的项目分配信息
 * @author panghu
 */
@Data
public class AllocationInfo2 implements Serializable {

    @ApiModelProperty
    private String id;

    @ApiModelProperty(value = "项目编号")
    private String projectId;

    @ApiModelProperty(value = "项目类别/类别")
    private String category;

    @ApiModelProperty(value = "项目负责人")
    private String leader;

    @ApiModelProperty(value = "项目说明")
    private String instruction;

    @ApiModelProperty("级别,省级,或者国家级")
    private String level;

    @ApiModelProperty("等级,一等奖,二等奖")
    private String grade;

    @ApiModelProperty("奖项数量")
    private Integer number;

    @ApiModelProperty("分数类型")
    private String variety;

    @ApiModelProperty("项目分数")
    private Integer score;

    @ApiModelProperty(value = "分配成员")
    private String username;

    @ApiModelProperty(value = "分配比例")
    private double proportion;

}
