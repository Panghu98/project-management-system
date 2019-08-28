package group.uchain.project.entity;

import lombok.Data;

/**
 * @author panghu
 */
@Data
public class PasswordUpdateForm {

    /**
     * 原密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

}
