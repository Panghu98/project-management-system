package group.uchain.panghu.controller;

import group.uchain.panghu.annotation.RoleRequired;
import group.uchain.panghu.entity.ProjectInfo;
import group.uchain.panghu.enums.RoleEnum;
import group.uchain.panghu.result.Result;
import group.uchain.panghu.service.InfoService;
import group.uchain.panghu.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author panghu
 * @title: InfoController
 * @projectName panghu
 * @date 19-7-15 上午10:02
 */
@RestController
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
    @PostMapping("/login")
    public Result login(Long userId, String password) {
        return loginService.login(userId, password);
    }


    @ApiOperation(value = "更改密码")
    @PostMapping("/updatePassword")
    public Result updatePassword(String password){
        return loginService.updatePassword(password);
    }

    @ApiOperation(value = "获取所有项目分数分配详情")
    @GetMapping("/info/getAllScore")
    public Result getAllScore(){

    }

}
