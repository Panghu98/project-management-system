package group.uchain.project_management_system.controller;

import group.uchain.project_management_system.annotation.RoleRequired;
import group.uchain.project_management_system.dto.RegisterUser;
import group.uchain.project_management_system.enums.RoleEnum;
import group.uchain.project_management_system.result.Result;
import group.uchain.project_management_system.service.FileService;
import group.uchain.project_management_system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author project_management_system
 * @title: FileController
 * @projectName project_management_system
 * @date 19-7-12 下午8:41
 */
@CrossOrigin(origins = "192.168.8.106:8080")
@RestController
@Api(tags = "超级管理员接口")
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
    @ApiOperation(value = "通过Excel导出个人项目分数分配信息")
    @PostMapping("/excel/projectUpload")
    public Result uploadFile(MultipartFile file){
        return fileService.uploadFile(file);
    }

    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "通过Excel表格注册用户")
    @PostMapping("/excel/register")
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
    @ApiOperation(value = "获取文件所有的文件名")
    @GetMapping("/file/getAllFilesName")
    public Result getAllFilesName(){
        return fileService.getAllFilesName();
    }


    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "超级管理员进行zip文件下载")
    @GetMapping("/file/downloadZipFile")
    public void downloadZipFile(@RequestParam(value = "files") List<String> files, HttpServletResponse response ){
        fileService.downloadZipFile(files,response);
    }

    @ApiOperation(value = "超级管理员导出Excel--看每个项目具体分给了哪些老师")
    @GetMapping("/file/getAllocationExcel")
    public Result getAllocationExcel(HttpServletResponse response){
        return fileService.getAllocationExcel(response);
    }
}
