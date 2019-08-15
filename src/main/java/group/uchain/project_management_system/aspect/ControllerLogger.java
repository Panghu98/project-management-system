package group.uchain.project_management_system.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author project_management_system
 * @Title: ControllerLogger
 * @ProjectName oil-supply-chain
 * @Description: 注意导入的包是     compile group: 'org.springframework.boot', name: 'spring-boot-starter-aop', version: '2.1.6.RELEASE'
 * 而不是Aspectj要不不起作用
 * @date 19-2-24 下午3:31
 */
@Aspect
@Component
@Slf4j
public class ControllerLogger {

    @Pointcut("execution(public * group.uchain.project_management_system.controller.*.*(..))")
    public void service() {

    }

    @Before("service()")
    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String method = signature.getDeclaringTypeName() + "." + signature.getName();
        log.info("-------------------------------");
        log.info("执行Service方法 : " + method);
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info("参数: " + arg);
        }
        log.info("-------------------------------");
    }


    @AfterReturning(pointcut = "service()", returning = "result")
    public void afterReturn(Object result) {
        log.info("--------------------------");
        log.info("返回参数 : " + result);
        log.info("-------------------------");
    }


}
