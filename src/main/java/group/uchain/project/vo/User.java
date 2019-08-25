package group.uchain.project.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author project
 * @title: User
 * @projectName project
 * @date 19-7-15 上午10:09
 */
@Data
public class User implements Serializable {


    /**
     * 用户ID  主键
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
