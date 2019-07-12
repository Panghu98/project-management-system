package group.uchain.panghu.service.impl;

import group.uchain.panghu.annotation.RoleRequired;
import group.uchain.panghu.enums.CodeMsg;
import group.uchain.panghu.enums.RoleEnum;
import group.uchain.panghu.exception.MyException;
import group.uchain.panghu.result.Result;
import group.uchain.panghu.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author panghu
 * @title: FileServiceImpl
 * @projectName panghu
 * @date 19-7-12 下午8:24
 */
@Service
@Slf4j
@Api(tags = "文件操作接口")
public class FileServiceImpl implements FileService {

    @Value(value = "${upload.path}")
    private String filePath;

    @Override
    @RoleRequired(RoleEnum.SUPER_ADMIN)
    @ApiOperation(value = "文件上传--超管可用")
    public Result uploadFile(MultipartFile file) {
        if (file==null){
            throw new MyException(CodeMsg.FILE_EMPTY_ERROR);
        }

        try{

            String pathFile = filePath+file.getOriginalFilename();

            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathFile);
            Files.write(path,bytes);

        } catch (IOException e) {

            log.error("文件上传失败");
            e.printStackTrace();

            throw new MyException(CodeMsg.FILE_UPLOAD_FAILED);
        }

        log.info("文件上传成功");
        return new Result();
    }

}
