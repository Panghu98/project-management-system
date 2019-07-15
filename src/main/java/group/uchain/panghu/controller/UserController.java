package group.uchain.panghu.controller;

import group.uchain.panghu.result.Result;
import group.uchain.panghu.service.LoginService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panghu
 * @title: TestController
 * @projectName panghu
 * @date 19-7-12 下午6:18
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private LoginService loginService;


    @PostMapping("/updatePassword")
    public Result updatePassword(String password){
        return loginService.updatePassword(password);
    }




}
