package group.uchain.project_management_system.util;

public class RoleConvertUtil {

    public static String getDescription(String role){
        switch (role){
            case "3":
                return "超级管理员";
            case "2":
                return "负责人";
            default:
                return "教师";
        }
    }

}
