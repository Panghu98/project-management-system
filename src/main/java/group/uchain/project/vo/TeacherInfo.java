package group.uchain.project.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author panghu
 */
@Data
public class TeacherInfo {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("该项目中所获得的分数")
    private Integer score;

    @ApiModelProperty("划分比例")
    private double proportion;

    @Override
    public String toString() {
        return  "教师:" + username + " 划分比例:" + proportion + " 分数:"+score;
    }
}
