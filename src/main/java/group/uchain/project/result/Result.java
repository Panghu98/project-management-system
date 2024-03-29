package group.uchain.project.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import group.uchain.project.enums.CodeMsg;
import group.uchain.project.exception.MyException;
import lombok.Data;

/**
 * 结果返回类封装
 * @author panghu
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public Result() {
        this.code = 200;
        this.message = "操作成功";
    }

    /**
     * 用于错误处理
     * @param code 错误码 与 CodeMsg对应
     * @param message 错误提示信息
     */
    public Result(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public Result(T data) {
        this.code=200;
        this.message="success";
        this.data = data;
    }

    private Result(CodeMsg codeMsg) {
        if (codeMsg==null) {
            return;
        }
        this.code = codeMsg.getCode();
        this.message = codeMsg.getMsg();
    }


    public static <T> Result<T> successData(T data){
        return new Result<T>(data);
    }

    public static <T> Result<T> error(CodeMsg codeMsg){
        return new Result<>(codeMsg);
    }

    public static <T> Result<T> error(MyException exception){
        return new Result<T>(exception.getCode(),exception.getMessage());
    }

    public static  Result<String> error(Integer code,String msg){
        return new Result<>(code,msg);
    }
}
