package group.uchain.project_management_system.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import group.uchain.project_management_system.enums.CodeMsg;
import group.uchain.project_management_system.exception.MyException;
import lombok.Data;

/**
 * 结果返回类封装
 * @author clf
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
        CodeMsg codeMsg;
        return new Result<T>(exception.getCode(),exception.getMessage());
    }

    public static  Result<String> error(String msg){
        return new Result<>(msg);
    }
}
