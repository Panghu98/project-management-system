package group.uchain.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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

    @NonNull
    private Long userId;

    @NonNull
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
