package group.uchain.panghu.controller;

import group.uchain.panghu.result.Result;
import group.uchain.panghu.service.InfoService;
import group.uchain.panghu.service.UserService;
import group.uchain.panghu.vo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author panghu
 * @title: InfoController
 * @projectName panghu
 * @date 19-7-15 上午10:02
 */
@RequestMapping("/info")
@RestController
@Api(tags = "信息接口")
public class InfoController {

    private InfoService infoService;

    @Autowired
    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @ApiOperation(value = "获取所有的用户")
    @GetMapping("/getAllUser")
    public Result getAllUser(){
        return infoService.getAllUser();
    }

    @ApiOperation(value = "上传信息")
    @PostMapping("/uploadAllocationInfo")
    public Result uploadAllocationInfo(Map<Long,Integer> list,String projectId){
        return new Result();
    }

}
