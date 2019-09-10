package group.uchain.project.form;

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
