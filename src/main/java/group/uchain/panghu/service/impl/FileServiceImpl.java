package group.uchain.panghu.service.impl;

import group.uchain.panghu.annotation.RoleRequired;
import group.uchain.panghu.entity.ProjectInfo;
import group.uchain.panghu.enums.CodeMsg;
import group.uchain.panghu.enums.RoleEnum;
import group.uchain.panghu.exception.MyException;
import group.uchain.panghu.mapper.ProjectInfoMapper;
import group.uchain.panghu.result.Result;
import group.uchain.panghu.service.FileService;
import group.uchain.panghu.util.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author panghu
 * @title: FileServiceImpl
 * @projectName panghu
 * @date 19-7-12 下午8:24
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private ProjectInfoMapper projectInfoMapper;

    @Autowired
    public FileServiceImpl(ProjectInfoMapper projectInfoMapper) {
        this.projectInfoMapper = projectInfoMapper;
    }

    @Value(value = "${upload.path}")
    private String filePath;

    @Override
    public Result uploadFile(MultipartFile file) {
        if (file==null){
            throw new MyException(CodeMsg.FILE_EMPTY_ERROR);
        }

        String pathFile = filePath+file.getOriginalFilename();

        try{


            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathFile);
            Files.write(path,bytes);

        } catch (IOException e) {

            log.error("文件上传失败");
            e.printStackTrace();

            throw new MyException(CodeMsg.FILE_UPLOAD_FAILED);
        }

        //读取Excel表格 存入数据库
        List<ProjectInfo> list = ExcelUtil.importXLS(pathFile);
        projectInfoMapper.readExcel(list);
        log.info("文件上传成功");
        return new Result();

    }

}
