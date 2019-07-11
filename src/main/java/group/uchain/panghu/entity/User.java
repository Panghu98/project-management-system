package group.uchain.panghu.entity;

import lombok.Data;

/**
 * @author panghu
 * @title: User
 * @projectName panghu
 * @date 19-7-10 下午8:04
 */
@Data
public class User {

    private Long userId;

    private String username;

    private String password;

    private String role;

    private String salt;

}
