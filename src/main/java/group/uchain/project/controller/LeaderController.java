package group.uchain.project.controller;

import com.alibaba.fastjson.JSONObject;
import group.uchain.project.dto.ProjectInfo;
import group.uchain.project.entity.ApplyForm;
import group.uchain.project.result.Result;
import group.uchain.project.service.ApplyService;
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
@CrossOrigin(origins = "192.168.8.101:8080")
@Api(tags = "负责人接口")
@RequestMapping(produces = { "application/json;charset=UTF-8" })
@RestController
public class LeaderController {

    private InfoService infoService;

    private FileService fileService;

    private ApplyService applyService;

    @Autowired
    public LeaderController(InfoService infoService, FileService fileService, ApplyService applyService) {
        this.infoService = infoService;
        this.fileService = fileService;
        this.applyService = applyService;
    }



    @ApiOperation(value = "上传分数分配信息")
    @PostMapping("/uploadAllocationInfo")
    public Result uploadAllocationInfo(@RequestBody JSONObject jsonObject){
        return infoService.uploadAllocationInfo(jsonObject);
    }

    @ApiOperation(value = "负责人de个人所有项目信息")
    @GetMapping("/info/getAllProjectInfoByUserId")
    public Result<List<ProjectInfo>> getAllProjectInfoByUserId(){
        return infoService.getAllProjectInfoByUserId();
    }

    @ApiOperation(value = "上传项目的相关证明材料")
    @PostMapping("/file/uploadEvidentFile")
    public Result uploadEvidentFile(MultipartFile file, String projectId){
        return fileService.uploadEvidentFile(file,projectId);
    }

    @ApiOperation(value = "负责人发起项目修改申请")
    @PostMapping("/action/apply")
    public Result apply(@Valid @RequestBody ApplyForm applyForm){
        return applyService.apply(applyForm);
    }


    @ApiOperation(value = "获取申请进度")
    @GetMapping("/info/getApplyDetail")
    public Result getApplyDetail(){
        return applyService.getApplyDetail();
    }

}
