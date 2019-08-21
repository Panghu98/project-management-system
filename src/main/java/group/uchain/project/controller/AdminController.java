package group.uchain.project.controller;

import group.uchain.project.annotation.RoleRequired;
import group.uchain.project.dto.ProjectInfo;
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
import org.springframework.context.annotation.Role;
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
@CrossOrigin(origins = "*")
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
    @ApiOperation(value = "通过表单进行用户注册")
    @PostMapping("/action/register")
    public Result registerByForm(RegisterUser registerUser){
        return userService.register(registerUser);
    }

    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "更新用户信息")
    @GetMapping("/action/updateUserInfo")
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

    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "文件下载--Excel查看:查看项目分数分配信息")
    @GetMapping("/file/getAllocationExcel")
    public Result getAllocationExcel(@RequestParam("start") Date startDate,@RequestParam("end") Date enddate, HttpServletResponse response){
        return fileService.getAllocationExcel(startDate,enddate,response);
    }

    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "获取所有的项目信息")
    @GetMapping("/info/getAllProjectInfo")
    public Result getAllProjectInfo(){
        return infoService.getAllProjectInfo();
    }

    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "修改项目信息")
    @PostMapping(value = "/action/updateProjectInfo")
    public Result updateProjectInfo(ProjectInfo projectInfo){
        return infoService.updateProjectInfo(projectInfo);
    }

    /**
     * 通过ID删除项目
     * @param id  项目ID
     * @return
     */
    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "删除项目信息")
    @PostMapping(value = "/action/deleteProjectInfo")
    public Result deleteProjectInfo(String id){
        return infoService.deleteProjectInfo(id);
    }

    /**
     * @param id  项目编号ID
     * @param date 截止日期
     * @return
     */
    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "设置项目分配信息提交的截止日期")
    @PostMapping("/action/setDeadline")
    public Result setDeadline(String id,Long date){
        return infoService.setDeadline(id,date);
    }

}
