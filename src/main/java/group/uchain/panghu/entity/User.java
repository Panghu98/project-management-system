package group.uchain.panghu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import group.uchain.panghu.dto.RegisterUser;
import group.uchain.panghu.util.MD5Util;
import group.uchain.panghu.util.SaltUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author panghu
 * @title: User
 * @projectName panghu
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
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户角色
     */
    private String role;

    /**
     * 用于加密的随机盐值
     */
    private String salt;

    /**
     * 教师职称
     */
    private String position;

    /**
     * 教师所处的教研室
     */
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
