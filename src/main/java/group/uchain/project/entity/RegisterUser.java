package group.uchain.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

/**
 * @author project
 * @title: User
 * @projectName project
 * @date 19-7-15 下午4:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {


    @NotNull(message = "ID不能为空")
    private Long userId;

    @NotNull(message = "用户名不能为空")
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
