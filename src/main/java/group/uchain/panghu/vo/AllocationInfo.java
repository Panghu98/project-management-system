package group.uchain.panghu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author panghu
 * @title: AllocationInfo
 * @projectName panghu
 * @date 19-7-21 下午3:30
 */
@Data
@ApiModel(value = "项目分配信息")
public class AllocationInfo {


    @ApiModelProperty(value = "项目编号")
    private String projectId;

    @ApiModelProperty(value = "成绩")
    private Double userScore;

    /**
     * 类别
     */
    @ApiModelProperty(value = "项目类别")
    private String category;

    @ApiModelProperty(value = "项目负责人")
    private String leader;

    /**
     * 项目说明
     */
    @ApiModelProperty(value = "项目说明")
    private String instruction;

}
