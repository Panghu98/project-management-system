package group.uchain.panghu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author panghu
 * @title: ProjectInfo
 * @projectName panghu
 * @date 19-7-13 下午9:17
 */
@Data
@ApiModel(description = "项目信息")
public class ProjectInfo {

    /**
     * 主键
     */
    @ApiModelProperty(value = "项目编号")
    private String id;

    /**
     * 类别
     */
    @ApiModelProperty(value = "项目类别")
    private String category;

    /**
     * 项目说明
     */
    @ApiModelProperty(value = "项目说明")
    private String instruction;

    /**
     * 级别 省级 国家级 等
     */
    @JsonIgnore
    private String level;

    /**
     * 等级
     */
    @JsonIgnore
    private String grade;

    /**
     * 项目数量
     */
    @JsonIgnore
    private Double number;

    /**
     * 类型
     */
    private String variety;

    /**
     * 分数
     */
    @JsonIgnore
    private Double score;

    /**
     * 负责人
     */
    @JsonIgnore
    private String leader;

    /**
     * 负责人最小划分比例
     */
    private Integer division;

}