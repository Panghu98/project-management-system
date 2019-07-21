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
    PASSWORD_UPDATE_ERROR(6,"新密码和原密码不能一样"),

    //文件出错
    FILE_UPLOAD_FAILED(10,"文件上传服务器失败"),
    FILE_EMPTY_ERROR(11,"文件为空"),
    XLS_FILE_READ_ERROR(12,"XLS文件读取异常"),
    XLS_FILE_FORMAT_ERROR(13,"表格格式有误"),
    //不能动这个
    PROJECT_ID_HAS_EXISTED(14,"项目编号已经存在"),
    ZIP_FILE_PACKAGE_ERROR(15,"文件压缩出错"),
    FILE_IS_EMPTY(16,"当前目录下文件为空"),

    //文件格式要求出错
    USER_HAS_EXISTED(20,"用户已经存在"),
    ALL_USERS_HAS_EXISTED(21,"所有的用户都已经注册"),

    PROJECT_ID_NOI_EXIST(30,"项目编号不存在"),

    DATABASE_ERROR(101,"数据库异常");


    private Integer code;
    private String msg;

}
