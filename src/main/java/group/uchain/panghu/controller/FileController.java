package group.uchain.panghu.controller;

import group.uchain.panghu.result.Result;
import group.uchain.panghu.service.FileService;
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
public class FileController {

    private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/test/upload")
    public Result uploadFile(MultipartFile file){
        return fileService.uploadFile(file);
    }
}
