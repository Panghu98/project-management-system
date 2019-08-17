package group.uchain.project_management_system.controller;

import group.uchain.project_management_system.entity.ProjectInfo;
import group.uchain.project_management_system.result.Result;
import group.uchain.project_management_system.service.FileService;
import group.uchain.project_management_system.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author project_management_system
 * @title: LeaderController
 * @projectName project_management_system
 * @date 19-7-21 上午10:26
 */
@CrossOrigin(origins = "192.168.8.106:8080")
@Api(tags = "负责人接口")
@RestController
public class LeaderController {

    private InfoService infoService;

    private FileService fileService;

    @Autowired
    public LeaderController(InfoService infoService,FileService fileService) {
        this.infoService = infoService;
        this.fileService = fileService;
    }

    @ApiOperation(value = "上传分数分配信息")
    @PostMapping("/uploadAllocationInfo")
    public Result uploadAllocationInfo(Map<Long,Integer> map, String projectId){
        return infoService.uploadAllocationInfo(map,projectId);
    }

    @ApiOperation(value = "负责人de个人所有项目信息")
    @GetMapping("/info/getAllProjectInfo")
    public Result<List<ProjectInfo>> getAllProjectInfo(){
        return infoService.getAllProjectInfo();
    }

    @ApiOperation(value = "上传项目的相关证明材料")
    @PostMapping("/file/uploadEvidentFile")
    public Result uploadEvidentFile(MultipartFile file){
        return fileService.uploadEvidentFile(file);
    }


}
