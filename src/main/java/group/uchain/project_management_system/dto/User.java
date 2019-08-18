package group.uchain.project_management_system.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import group.uchain.project_management_system.dto.RegisterUser;
import group.uchain.project_management_system.util.MD5Util;
import group.uchain.project_management_system.util.SaltUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author project_management_system
 * @title: User
 * @projectName project_management_system
 * @date 19-7-10 下午8:04
 */
@Data
@NoArgsConstructor
public class User {

    @JsonIgnore
    @Value(value = "${default.password}")
    private String defaultPassword;

    /**
     * 教师工号
     */
    @ApiModelProperty(value = "教师工号")
    private Long userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "教师用户名")
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户角色
     */
    @ApiModelProperty(value = "教师角色  1，普通教师 2，项目负责人")
    private String role;

    /**
     * 用于加密的随机盐值
     */
    @JsonIgnore
    private String salt;

    /**
     * 教师职称
     */
    @ApiModelProperty(value = "教师职称")
    private String position;

    /**
     * 教师所处的教研室
     */
    @ApiModelProperty(value = "教师所属的教研室")
    private String office;



    public User(RegisterUser registerUser) {

        this.setUserId(registerUser.getUserId());
        this.setUsername(registerUser.getUsername());
        this.setPosition(registerUser.getPosition());
        this.setOffice(registerUser.getOffice());
        this.setRole("1");
        String salt = SaltUtil.getSalt();
        this.setSalt(salt);
        String password = MD5Util.inputPassToDBPass(defaultPassword,salt);
        this.setPassword(password);

    }
}
