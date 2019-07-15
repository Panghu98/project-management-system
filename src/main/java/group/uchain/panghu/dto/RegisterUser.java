package group.uchain.panghu.dto;

import lombok.Data;

/**
 * @author panghu
 * @title: User
 * @projectName panghu
 * @date 19-7-15 下午4:05
 */
@Data
public class RegisterUser {


    private Long userId;

    private String username;


    /**
     * 教师职称
     */
    private String position;

    /**
     * 教师所属教研室
     */
    private String office;

}
