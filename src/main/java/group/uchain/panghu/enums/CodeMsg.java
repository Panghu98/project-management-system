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
    PASSWORD_UPDATE_ERROR(7,"新密码和原密码不能一样"),
    FILE_UPLOAD_FAILED(8,"文件上传服务器失败"),
    FILE_EMPTY_ERROR(9,"文件为空"),
    XLS_FILE_READ_ERROR(10,"XLS文件读取异常"),
    XLS_FILE_FORMAT_ERROR(11,"表格格式有误"),
    PROJECT_ID_HAS_EXISTED(14,"项目编号已经存在"),
    USER_HAS_EXISTED(20,"用户已经存在"),
    ALL_USERS_HAS_EXISTED(21,"所有的用户都已经注册");


    private Integer code;
    private String msg;

}
