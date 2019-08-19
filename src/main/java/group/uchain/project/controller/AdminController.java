package group.uchain.project.controller;

import group.uchain.project.annotation.RoleRequired;
import group.uchain.project.dto.RegisterUser;
import group.uchain.project.enums.RoleEnum;
import group.uchain.project.result.Result;
import group.uchain.project.service.FileService;
import group.uchain.project.service.InfoService;
import group.uchain.project.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author project
 * @title: FileController
 * @projectName project
 * @date 19-7-12 下午8:41
 */
@CrossOrigin(origins = "192.168.8.106:8080")
@RestController
@Api(tags = "超级管理员接口")
public class AdminController {

    private FileService fileService;

    private UserService userService;

    private InfoService infoService;

    @Autowired
    public AdminController(FileService fileService,UserService userService,
                           InfoService infoService) {
        this.userService = userService;
        this.fileService = fileService;
        this.infoService = infoService;
    }

    /**
     * 超级管理员通过导入Excel表格进行文件信息上传
     * @param file
     * @return
     */
    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "Excel导入--项目信息导入")
    @PostMapping("/excel/projectUpload")
    public Result importProjectByExcel(MultipartFile file){
        return fileService.importProjectByExcel(file);
    }

    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "Excel导入--表格注册用户")
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
    @ApiOperation(value = "文件下载--指定文件名")
    @GetMapping("/file/downloadZipFile")
    public void downloadZipFile(@RequestParam(value = "files") List<String> files, HttpServletResponse response ){
        fileService.downloadZipFile(files,response);
    }

    @ApiOperation(value = "文件下载--Excel查看每个项目具体分给了哪些老师")
    @GetMapping("/file/getAllocationExcel")
    public Result getAllocationExcel(@RequestParam("start") Date startDate,@RequestParam("end") Date enddate, HttpServletResponse response){
        return fileService.getAllocationExcel(startDate,enddate,response);
    }

    @ApiOperation(value = "查看项目分配信息")
    @GetMapping("/info/getAllProjectInfo")
    public Result getAllProjectInfo(){
        return infoService.getAllProjectInfo();
    }

}
