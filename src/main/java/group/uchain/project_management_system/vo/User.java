package group.uchain.project_management_system.vo;

import lombok.Data;

/**
 * @author project_management_system
 * @title: User
 * @projectName project_management_system
 * @date 19-7-15 上午10:09
 */
@Data
public class User {

    /**
     * 用户ID
     */

    private Long userId;

    /**
     * 用户姓名
     */
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
