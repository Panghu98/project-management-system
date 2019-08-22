package group.uchain.project.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @NonNull
    private String level;

    /**
     * 等级
     */
    @JsonIgnore
    @NonNull
    private String grade;

    /**
     * 项目数量
     */
    @JsonIgnore
    @NonNull
    private String number;

    /**
     * 类型
     */
    @NonNull
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

    @JSONField(format = "yyyy-MM-dd")
    private Date date;

    /**
     * 项目时候分配  1,已经分配 0,未分配
     */
    private Integer allocationStatus;

    @JSONField(format = "yyyy-MM-dd")
    private Date deadline;


}
