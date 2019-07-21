package group.uchain.panghu.entity;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author panghu
 * @title: LoginUser
 * @projectName panghu
 * @date 19-7-21 下午4:34
 */
@Data
public class LoginUser {

    private Long userId;

    private String password;

}
