package group.uchain.panghu.service.impl;

import group.uchain.panghu.dto.RegisterUser;
import group.uchain.panghu.entity.ProjectInfo;
import group.uchain.panghu.entity.User;
import group.uchain.panghu.enums.CodeMsg;
import group.uchain.panghu.exception.MyException;
import group.uchain.panghu.mapper.ProjectInfoMapper;
import group.uchain.panghu.mapper.UserFormMapper;
import group.uchain.panghu.result.Result;
import group.uchain.panghu.service.FileService;
import group.uchain.panghu.util.ExcelUtil;
import group.uchain.panghu.util.MD5Util;
import group.uchain.panghu.util.SaltUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    private UserFormMapper userFormMapper;

    private ProjectInfoMapper projectInfoMapper;

    @Autowired
    public FileServiceImpl(ProjectInfoMapper projectInfoMapper,UserFormMapper userFormMapper) {
        this.projectInfoMapper = projectInfoMapper;
        this.userFormMapper = userFormMapper;
    }

    @Value(value = "${filepath.project-excel}")
    private String filePath;


    @Value(value = "${filepath.register-excel}")
    private String filePath2;


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
        List<ProjectInfo> list = ExcelUtil.importProjectXLS(pathFile);
        projectInfoMapper.readExcel(list);
        log.info("文件上传成功");
        return new Result();

    }

    @Override
    public Result registerByExcel(MultipartFile file) {
        if (file==null){
            throw new MyException(CodeMsg.FILE_EMPTY_ERROR);
        }

        String pathFile = filePath2+file.getOriginalFilename();

        try{
            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathFile);
            Files.write(path,bytes);

        } catch (IOException e) {

            e.printStackTrace();
            log.error("文件上传失败");
            throw new MyException(CodeMsg.FILE_UPLOAD_FAILED);
        }

        //读取Excel表格存入数据库
        List<RegisterUser> list = ExcelUtil.getUsersByExcel(pathFile);
        List<User> users = new ArrayList<>();
        for (RegisterUser registerUser:list
             ) {
            User user = new User(registerUser);
            //如果用户已经存在则不需要加入List当中在进行注册
            if (userFormMapper.selectUserByUserId(user.getUserId())==null){
                users.add(user);
            }else {
                log.error("工号为{}的用户已经存在",registerUser.getUserId());
            }
        }

        //将用户数组插入数据库
        if (users.size()==0){
            return Result.error(CodeMsg.ALL_USERS_HAS_EXISTED);
        }else {
            userFormMapper.registerMultiUser(users);
        }
        return Result.successData(users);

    }

}
