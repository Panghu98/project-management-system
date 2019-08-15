package group.uchain.project_management_system.controller;

import group.uchain.project_management_system.annotation.RoleRequired;
import group.uchain.project_management_system.entity.LoginUser;
import group.uchain.project_management_system.enums.RoleEnum;
import group.uchain.project_management_system.result.Result;
import group.uchain.project_management_system.service.InfoService;
import group.uchain.project_management_system.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author project_management_system
 * @title: InfoController
 * @projectName project_management_system
 * @date 19-7-15 上午10:02
 */
@CrossOrigin(origins = "192.168.8.106")
@RestController
@RequestMapping(produces = { "application/json;charset=UTF-8" })
@Api(tags = "公共接口")
public class PublicController {

    private InfoService infoService;

    private LoginService loginService;

    @Autowired
    public PublicController(InfoService infoService,LoginService loginService) {
        this.infoService = infoService;
        this.loginService = loginService;
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
    @ApiOperation(value = "用户登录接口",notes = "用户登录")
    @PostMapping(value = "/login")
    public Result login(LoginUser loginUser, HttpServletRequest request) {
        return loginService.login(loginUser.getUserId(),loginUser.getPassword(),request);
    }


    @ApiOperation(value = "更改密码")
    @PostMapping("/updatePassword")
    public Result updatePassword(String password){
        return loginService.updatePassword(password);
    }

    @ApiOperation(value = "获取所有项目分数分配详情")
    @GetMapping("/info/getAllScore")
    public Result getAllScore(){
        return infoService.getAllScore();
    }

}
