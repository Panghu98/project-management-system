package group.uchain.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Date;

/**
 * @author project
 * @title: ProjectInfo
 * @projectName project
 * @date 19-7-13 下午9:17
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "项目信息")
public class ProjectInfo implements Serializable{

    public ProjectInfo() {
    }

    private static final long serialVersionUID = 3171800993165251636L;
    /**
     * 主键
     */
    @NonNull
    @ApiModelProperty(value = "项目编号")
    private String id;

    /**
     * 类别
     */
    @NonNull
    @ApiModelProperty(value = "项目类别")
    private String category;

    /**
     * 项目说明
     */
    @NonNull
    @ApiModelProperty(value = "项目说明")
    private String instruction;

    /**
     * 级别 省级 国家级 等
     */
    @ApiModelProperty("级别,省级,或者国家级")
    private String level;

    /**
     * 等级
     */
    @ApiModelProperty("等级,一等奖,二等奖")
    private String grade;

    /**
     * 奖项数量
     */
    @ApiModelProperty("奖项数量")
    private Double number;

    /**
     * 分数类型
     */
    @ApiModelProperty("分数类型")
    private String variety;

    /**
     * 分数
     */
    @NonNull
    private Double score;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 负责人最小划分比例
     */
    @NonNull
    private Integer division;

    /**
     * 项目导入时间
     */

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date date;

    /**
     * 项目时候分配  2.已分配 1,申请中 0,未分配
     */
    private Integer allocationStatus;

    private Date deadline;

    /**
     * 文件上传状态
     * 0:未上传
     * 1:已上传
     */
    private Integer fileUploadStatus;

}
