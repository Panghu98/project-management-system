package group.uchain.project.controller;

import group.uchain.project.annotation.RoleRequired;
import group.uchain.project.DTO.ProjectInfo;
import group.uchain.project.form.DeadLineForm;
import group.uchain.project.form.ModifyScoreForm;
import group.uchain.project.form.TimeLimit;
import group.uchain.project.enums.RoleEnum;
import group.uchain.project.result.Result;
import group.uchain.project.service.FileService;
import group.uchain.project.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @author project
 * @title: FileController
 * @projectName project
 * @date 19-7-12 下午8:41
 */
@CrossOrigin(origins = "*")
@RestController
@Api(tags = "超级管理员项目接口")
public class AdminProjectController {

    private FileService fileService;


    private InfoService infoService;

    @Autowired
    public AdminProjectController(FileService fileService, InfoService infoService) {
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
    @ApiOperation(value = "获取文件所有的文件名")
    @GetMapping("/file/getAllFilesName")
    public Result getAllFilesName(){
        return fileService.getAllFilesName();
    }


    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "多文件下载--指定文项目ID")
    @PostMapping("/file/downloadZipFile")
    public void downloadZipFile(@RequestParam(value = "idList") List<String> idList, HttpServletResponse response ){
        fileService.downloadZipFile(idList,response);
    }

    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "单文件下载--指定项目ID")
    @PostMapping("/file/downloadSingleFile")
    public void downloadSingleFile(@RequestBody String id, HttpServletResponse response, HttpServletRequest request){
        fileService.downloadSingleFile(id,response,request);
    }


    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "文件下载--Excel查看:查看项目分数分配信息")
    @PostMapping("/file/getAllocationExcel")
    public void getAllocationExcel(@RequestBody TimeLimit timeLimit, HttpServletResponse response){
        System.err.println(timeLimit);
        fileService.getAllocationExcel(timeLimit.getStart(),timeLimit.getEnd(),response);
    }

    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "获取所有wei设置截止日期的项目信息")
    @GetMapping("/info/getAllProjectInfo")
    public Result getAllProjectInfo(){
        return infoService.getAllProjectInfo();
    }

    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "获取所有yi设置截止日期的项目信息")
    @GetMapping("/info/getDeadlineProjectInfo")
    public Result getDeadlineProjectInfo(){
        return infoService.getDeadlineProjectInfo();
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
    @GetMapping(value = "/action/deleteProjectInfo")
    public Result deleteProjectInfo(String id){
        return infoService.deleteProjectInfo(id);
    }

    /**
     * @return
     */
    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "设置项目分配信息提交的截止日期")
    @PostMapping("/action/setDeadline")
    public Result setDeadline(@Valid @RequestBody DeadLineForm form) throws InterruptedException {
        return infoService.setDeadline(form.getId(), form.getDate());
    }

    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "更改项目分数")
    @PostMapping("/action/modifyScore")
    public Result modifyScore(@Valid @RequestBody ModifyScoreForm modifyScoreForm){
        return infoService.modifyScore(modifyScoreForm);
    }




}
