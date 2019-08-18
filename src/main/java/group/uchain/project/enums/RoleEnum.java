package group.uchain.project.enums;

import lombok.Getter;

/**
 * @author project
 * @Title: RoleEnum
 * @ProjectName oil-supply-chain
 * @Description: TODO
 * @date 19-2-24 下午5:01
 */
@Getter
public enum RoleEnum {

    /***/
    TEACHER("1","普通教师"),
    PROJECT_LEADER("2","项目负责人"),
    SUPER_ADMIN("3","超级管理员");


    private String role;

    private String tips;

    RoleEnum(String role, String tips) {
        this.role = role;
        this.tips = tips;
    }}
