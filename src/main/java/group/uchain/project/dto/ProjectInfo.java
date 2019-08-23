package group.uchain.project.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
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
    @ApiModelProperty("级别,省级,或者国家级")
    private String level;

    /**
     * 等级
     */
    @JsonIgnore
    @ApiModelProperty("等级,一等奖,二等奖")
    private String grade;

    /**
     * 奖项数量
     */
    @JsonIgnore
    @ApiModelProperty("奖项数量")
    private Integer number;

    /**
     * 分数类型
     */
    @NonNull
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
     * 项目时候分配  1,已经分配 0,未分配
     */
    private Integer allocationStatus;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date deadline;

    @Override
    public String toString() {
        return "ProjectInfo{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", instruction='" + instruction + '\'' +
                ", level='" + level + '\'' +
                ", grade='" + grade + '\'' +
                ", number='" + number + '\'' +
                ", variety='" + variety + '\'' +
                ", score=" + score +
                ", leader='" + leader + '\'' +
                ", division=" + division +
                ", date=" + date +
                ", allocationStatus=" + allocationStatus +
                ", deadline=" + deadline +
                '}';
    }
}
