package group.uchain.project.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author project
 * @title: LoginUser
 * @projectName project
 * @date 19-7-21 下午4:34
 */
@Data
public class LoginUser {

    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotNull(message = "密码不能为空")
    private String password;

}
