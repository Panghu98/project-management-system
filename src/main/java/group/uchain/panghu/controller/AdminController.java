package group.uchain.panghu.controller;

import group.uchain.panghu.annotation.RoleRequired;
import group.uchain.panghu.dto.RegisterUser;
import group.uchain.panghu.enums.RoleEnum;
import group.uchain.panghu.result.Result;
import group.uchain.panghu.service.FileService;
import group.uchain.panghu.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author panghu
 * @title: FileController
 * @projectName panghu
 * @date 19-7-12 下午8:41
 */
@CrossOrigin
@RestController
@Api(tags = "文件操作接口")
public class AdminController {

    private FileService fileService;

    private UserService userService;

    @Autowired
    public AdminController(FileService fileService,UserService userService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    /**
     * 超级管理员通过导入Excel表格进行文件信息上传
     * @param file
     * @return
     */
    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "文件上传--超管可用")
    @PostMapping("/register-excel/upload")
    public Result uploadFile(MultipartFile file){
        return fileService.uploadFile(file);
    }

    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "通过Excel表格注册用户")
    @PostMapping("/register-excel/register")
    public Result registerByExcel(MultipartFile file){
        return fileService.registerByExcel(file);
    }

    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "超级管理员进行用户注册")
    @PostMapping("/admin/register")
    public Result registerByForm(RegisterUser registerUser){
        return userService.register(registerUser);
    }

    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "更新用户信息")
    @GetMapping("/updateUserInfo")
    public Result updateInfo(){
        return new Result();
    }

    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "超级管理员进行文件下载")
    @GetMapping("/file/downloadZipFile")
    public void downloadZipFile(HttpServletResponse response, List<String> files){
        fileService.downloadZipFile(files,response);
    }
}
