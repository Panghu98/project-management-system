package group.uchain.project.dto;

import lombok.Data;

/**
 * @author project
 * @title: User
 * @projectName project
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
