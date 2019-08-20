package group.uchain.project.aspect;


import group.uchain.project.exception.MyException;
import group.uchain.project.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author project
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
    public Result handleSelfException(MyException exception){
        log.error(EXCEPTION_MSG_KEY+exception.getMessage());
        return Result.error(exception);
    }

    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public void handleNullPointException(MyException exception){
        log.error(EXCEPTION_MSG_KEY+exception.getMessage());
    }

}
