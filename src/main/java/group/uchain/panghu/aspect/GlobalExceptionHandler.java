package group.uchain.panghu.aspect;


import group.uchain.panghu.enums.CodeMsg;
import group.uchain.panghu.exception.MyException;
import group.uchain.panghu.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * @author panghu
 * @title: GlobalExceptionHandler
 * @projectName oil-supply-chain
 * @date 19-4-7 下午5:28
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private final static String EXCEPTION_MSG_KEY = "Exception message : ";

    @ResponseBody
    @ExceptionHandler(MyException.class)
    public Result handleOilException(MyException exception){
        log.error(EXCEPTION_MSG_KEY+exception.getMessage());
        return Result.error(exception);
    }

    @ResponseBody
    @ExceptionHandler(SQLException.class)
    public Result<Object> handleSQLException(SQLException exception){
        log.error(EXCEPTION_MSG_KEY+exception.getMessage());
        return Result.error(CodeMsg.DATABASE_ERROR);
    }


}
