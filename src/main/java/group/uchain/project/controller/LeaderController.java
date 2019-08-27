package group.uchain.project.controller;

import group.uchain.project.dto.ProjectInfo;
import group.uchain.project.entity.Allocation;
import group.uchain.project.result.Result;
import group.uchain.project.service.FileService;
import group.uchain.project.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * @author project
 * @title: LeaderController
 * @projectName project
 * @date 19-7-21 上午10:26
 */
@CrossOrigin(origins = "192.168.8.106:8080")
@Api(tags = "负责人接口")
@RequestMapping(produces = { "application/json;charset=UTF-8" })
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
    public Result uploadAllocationInfo(@Valid @RequestBody Allocation allocation){
        return infoService.uploadAllocationInfo(allocation);
    }

    @ApiOperation(value = "负责人de个人所有项目信息")
    @GetMapping("/info/getAllProjectInfoByUserId")
    public Result<List<ProjectInfo>> getAllProjectInfoByUserId(){
        return infoService.getAllProjectInfoByUserId();
    }

    @ApiOperation(value = "上传项目的相关证明材料")
    @GetMapping("/file/uploadEvidentFile")
    public Result uploadEvidentFile(MultipartFile file){
        return fileService.uploadEvidentFile(file);
    }



}
