package group.uchain.panghu.controller;

import group.uchain.panghu.result.Result;
import group.uchain.panghu.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panghu
 * @title: AnonController
 * @projectName panghu
 * @date 19-7-11 下午7:47
 */
@Api(description = "用户接口",tags = {"用户操作接口"})
@RequestMapping("/anon")
@RestController
@CrossOrigin
public class AnonController {

    private LoginService loginService;

    @Autowired
    public AnonController(LoginService loginService) {
        this.loginService = loginService;
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户名",required = true),
            @ApiImplicitParam(name = "password",value = "密码",required = true)
    })
    @ApiOperation(value = "用户登录接口",notes = "用户登录")
    @PostMapping("/login")
    public Result login(Long userId, String password) {
        return loginService.login(userId, password);
    }




}
