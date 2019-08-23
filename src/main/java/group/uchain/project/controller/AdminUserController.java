package group.uchain.project.controller;

import group.uchain.project.annotation.RoleRequired;
import group.uchain.project.dto.RegisterUser;
import group.uchain.project.enums.RoleEnum;
import group.uchain.project.result.Result;
import group.uchain.project.service.FileService;
import group.uchain.project.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author panghu
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(produces = { "application/json;charset=UTF-8" })
@Api(tags = "超级管理员用户操作接口")
public class AdminUserController {

    private UserService userService;

    private FileService fileService;

    @Autowired
    public AdminUserController(UserService userService,FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "通过表单进行用户注册")
    @PostMapping("/action/register")
    public Result registerByForm(@RequestBody RegisterUser registerUser){
        return userService.register(registerUser);
    }

    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "Excel导入--表格注册用户")
    @PostMapping("/excel/register")
    public Result registerByExcel(MultipartFile file){
        return fileService.registerByExcel(file);
    }

    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "表单注册用户")
    @PostMapping("/action/updateUserInfo")
    public Result updateUserInfo(@RequestBody RegisterUser registerUser){
        return userService.register(registerUser);
    }

    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "删除用户")
    @GetMapping("/action/deleteUser")
    public Result deleteUser(Long userId){
        return userService.deleteUser(userId);
    }


    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "重置普通教师密码")
    @GetMapping("/action/resetPassword")
    public Result resetPassword(Long userId){
        return userService.resetPassword(userId);
    }
}
