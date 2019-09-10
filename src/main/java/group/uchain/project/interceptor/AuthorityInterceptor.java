package group.uchain.project.interceptor;


import com.alibaba.fastjson.JSON;

import group.uchain.project.annotation.RoleRequired;
import group.uchain.project.DTO.User;
import group.uchain.project.enums.CodeMsg;
import group.uchain.project.result.Result;
import group.uchain.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dgh
 * @date 19-1-19 下午7:54
 */
@Component
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {

    private UserService userService;

    @Autowired
    public AuthorityInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String json;
        User user = userService.getCurrentUser();
        //Token中获取到用户为空  抛出交给 Filter处理
        if (user == null) {
            return true;
        }
        log.info("Security 执行权限验证--------");
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RoleRequired roleRequired = handlerMethod.getMethodAnnotation(RoleRequired.class);
            if (roleRequired == null) {
                return true;
            }
            /*将需要的权限对应的数字转化成字符数组,通过字符进行对比*/
            Integer requireValue = Integer.valueOf(roleRequired.value().getRole());
            Integer userValue = Integer.valueOf(user.getRole());
            if (requireValue == 0){
                return true;
            }
            log.info("requireValue:{},userValue:{}", requireValue, userValue);
                if (userValue >= requireValue) {
                    return true;
            }
        }
        json = JSON.toJSONString(Result.error(CodeMsg.PERMISSION_DENNY));
        log.error("............权限不足...........");
        response.getWriter().append(json);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
