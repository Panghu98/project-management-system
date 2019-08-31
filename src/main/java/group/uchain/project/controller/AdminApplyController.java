package group.uchain.project.controller;

import group.uchain.project.annotation.RoleRequired;
import group.uchain.project.entity.ApplyConfirmForm;
import group.uchain.project.enums.RoleEnum;
import group.uchain.project.result.Result;
import group.uchain.project.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author panghu
 */
@RestController
@Api(tags = "管理员申请操作接口")
public class AdminApplyController {


    private InfoService infoService;

    @Autowired
    public AdminApplyController(InfoService infoService) {
        this.infoService = infoService;
    }

    @RoleRequired(value = RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "获取所有申请信息")
    @GetMapping("/info/getAllApplyInfo")
    public Result getAllApplyInfo(){
        return infoService.getAllApplyInfo();
    }

    @RoleRequired(value = RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "审核申请信息")
    @PostMapping("/action/setApplyStatus")
    public Result setApplyStatus(@Valid @RequestBody ApplyConfirmForm applyConfirmForm){
        return infoService.setApplyStatus(applyConfirmForm);
    }


}
