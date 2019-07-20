package group.uchain.panghu.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author panghu
 * @title: User
 * @projectName panghu
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
    private String name;

    /**
     * 教师职称
     */
    private String position;

    /**
     * 教师所属教研室
     */
    private String office;
}
