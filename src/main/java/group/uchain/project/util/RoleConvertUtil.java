package group.uchain.project.util;

public class RoleConvertUtil {

    public static String getDescription(Integer role){
        switch (role){
            case 3:
                return "超级管理员";
            case 2:
                return "负责人";
            default:
                return "教师";
        }
    }

}
