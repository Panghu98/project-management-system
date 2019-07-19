package group.uchain.panghu.exception;

import group.uchain.panghu.enums.CodeMsg;
import lombok.Data;

/**
 * @author panghu
 * @title: 自定义异常类
 * @projectName panghu
 * @date 19-7-11 下午9:24
 */
@Data
public class MyException extends RuntimeException{

    private static final long serialVersionUID = 1071181645049295228L;

    private Integer code;

    public MyException(String message,Integer code) {
        super(message);
        this.code = code;
    }

    public MyException(CodeMsg codeMsg) {
        super(codeMsg.getMsg());
        this.code = codeMsg.getCode();
    }
}
