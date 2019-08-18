package group.uchain.project.vo;

import lombok.Data;

@Data
public class Teacher {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户ID
     */
    private String userId;

    @Override
    public String toString() {
        return "教师名称:"+username+" 教师工号"+userId+"\n";
    }
}
