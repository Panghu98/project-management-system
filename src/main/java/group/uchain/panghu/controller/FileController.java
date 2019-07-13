package group.uchain.panghu.controller;

import group.uchain.panghu.annotation.RoleRequired;
import group.uchain.panghu.enums.RoleEnum;
import group.uchain.panghu.result.Result;
import group.uchain.panghu.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author panghu
 * @title: FileController
 * @projectName panghu
 * @date 19-7-12 下午8:41
 */
@RestController
@Api(tags = "文件操作接口")
public class FileController {

    private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "文件上传--超管可用")
    @PostMapping("/test/upload")
    public Result uploadFile(MultipartFile file){
        return fileService.uploadFile(file);
    }
}
