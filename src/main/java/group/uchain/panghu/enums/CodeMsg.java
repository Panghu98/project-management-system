package group.uchain.panghu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author panghu
 */

@Getter
@AllArgsConstructor
public enum CodeMsg {
    /***/
    AUTHENTICATION_ERROR(1, "用户认证失败,请重新登录"),
    ORDER_NOT_EXIST(2, "订单不存在"),
    PERMISSION_DENNY(3, "权限不足"),
    USER_NOT_EXIST(4,"用户不存在"),
    PASSWORD_ERROR(5,"密码错误"),
    DATABASE_ERROR(6,"数据库异常"),
    PASSWORD_UPDATE_ERROR(7,"新密码和原密码不能一样");

    private Integer code;
    private String msg;

}
