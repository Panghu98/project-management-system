package group.uchain.project.controller;

import group.uchain.project.annotation.RoleRequired;
import group.uchain.project.form.LoginUser;
import group.uchain.project.form.PasswordUpdateForm;
import group.uchain.project.enums.RoleEnum;
import group.uchain.project.result.Result;
import group.uchain.project.service.InfoService;
import group.uchain.project.service.LoginService;
import group.uchain.project.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author project
 * @title: InfoController
 * @projectName project
 * @date 19-7-15 上午10:02
 */
@CrossOrigin(origins = "192.168.8.103:8080")
@RestController
@RequestMapping(produces = { "application/json;charset=UTF-8" })
@Api(tags = "公共接口")
public class PublicController {

    private InfoService infoService;

    private LoginService loginService;

    private UserService userService;

    @Autowired
    public PublicController(InfoService infoService,LoginService loginService,
                            UserService userService) {
        this.infoService = infoService;
        this.loginService = loginService;
        this.userService = userService;
    }

    @RoleRequired(value = RoleEnum.PROJECT_LEADER)
    @ApiOperation(value = "获取所有的用户")
    @GetMapping("/info/getAllUser")
    public Result getAllUser(){
        return infoService.getAllUser();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户名",required = true),
            @ApiImplicitParam(name = "password",value = "密码",required = true)
    })
    @RoleRequired(RoleEnum.TEACHER)
    @ApiOperation(value = "用户登录接口",notes = "用户登录")
    @PostMapping(value = "/login")
    public Result login(@Valid @RequestBody LoginUser loginUser, HttpServletRequest request) {
        return loginService.login(loginUser.getUserId(),loginUser.getPassword(),request);
    }


    @ApiOperation(value = "更改密码")
    @PostMapping("/action/updatePassword")
    public Result updatePassword(@RequestBody PasswordUpdateForm passwordUpdateForm, HttpServletRequest request){
        return userService.updatePassword(passwordUpdateForm,request);
    }

    @ApiOperation(value = "获取所有项目分数分配详情")
    @GetMapping("/info/getAllScore")
    public Result getAllScore(){
        return infoService.getAllScore();
    }


}
