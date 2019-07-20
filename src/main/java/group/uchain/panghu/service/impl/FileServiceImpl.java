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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    private String projectFilePath;


    @Value(value = "${filepath.register-excel}")
    private String registerFilePath;

    @Value(value = "${filepath.evident}")
    private String evidentFilePath;

    @Value(value = "${file.zip-name}")
    private String zipName;


    /**
     *
     * @param file  项目文件
     * @return
     */
    @Override
    public Result uploadFile(MultipartFile file) {
        if (file==null){
            throw new MyException(CodeMsg.FILE_EMPTY_ERROR);
        }

        String pathFile = projectFilePath+file.getOriginalFilename();

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
        List<String> listOfId = new ArrayList<>();
        for (ProjectInfo projectInfo : list) {
            String id = projectInfo.getId();
            listOfId.add(id);
        }
        List<String> idList = projectInfoMapper.getRepeatNums(listOfId);
        if(idList.size()!=0){
            throw new MyException("项目编号"+idList.toString()+"已经存在",14);
        }

        //插入数据库
        projectInfoMapper.readExcel(list);

        log.info("文件上传成功");
        return new Result();

    }

    @Override
    public Result registerByExcel(MultipartFile file) {
        if (file==null){
            throw new MyException(CodeMsg.FILE_EMPTY_ERROR);
        }

        String pathFile = registerFilePath+file.getOriginalFilename();

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

    @Override
    public HttpServletResponse downloadZipFile(List<String> fileNameList, HttpServletResponse response) {
        List<File> fileList = new ArrayList<>();
        for (String fileName:fileNameList
             ) {
            String pathFile = evidentFilePath+fileName;
            File file = new File(pathFile);
            fileList.add(file);
        }
        try {
            // List<File> 作为参数传进来，就是把多个文件的路径放到一个list里面
            // 创建一个临时压缩文件

            // 临时文件可以放在任意位置，但不建议这么做，因为需要先设置磁盘的访问权限
            // 最好是放在服务器上，方法最后有删除临时文件的步骤

            File file = new File(zipName);
            response.reset();
            // response.getWriter()
            // 创建文件输出流
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ZipOutputStream zipOut = new ZipOutputStream(fileOutputStream);
            zipFiles(fileList, zipOut);
            zipOut.close();
            fileOutputStream.close();
            return downloadZip(file, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private static HttpServletResponse downloadZip(File file, HttpServletResponse response) {
        if (!file.exists()) {
            System.out.println("待压缩的文件目录：" + file + "不存在.");
        } else {
            try {
                // 以流的形式下载文件。
                InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
                byte[] buffer = new byte[fis.available()];
                fis.close();
                // 清空response
                response.reset();

                OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/octet-stream");

                // 如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + new String(file.getName().getBytes("GB2312"), "ISO8859-1"));
                toClient.write(buffer);
                toClient.flush();
                toClient.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new MyException(CodeMsg.ZIP_FILE_PACKAGE_ERROR);
            }
        }
        return response;
    }

    /**
     * 把接受的全部文件打成压缩包
     */
    private static void zipFiles(List<File> files,ZipOutputStream outputStream){
        for (File file:files
             ) {
            zipFile(file,outputStream);
        }
    }

    /**
     * 根据输入的文件与输出流对文件进行打包
     */
    private static void zipFile(File inputFile, ZipOutputStream ouputStream) {
        try {
            if (inputFile.exists()) {
                if (inputFile.isFile()) {
                    FileInputStream fileInputStream = new FileInputStream(inputFile);
                    BufferedInputStream bins = new BufferedInputStream(fileInputStream, 512);
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    ouputStream.putNextEntry(entry);
                    // 向压缩文件中输出数据
                    int nNumber;
                    byte[] buffer = new byte[512];
                    while ((nNumber = bins.read(buffer)) != -1) {
                        ouputStream.write(buffer, 0, nNumber);
                    }
                    // 关闭创建的流对象
                    bins.close();
                    fileInputStream.close();
                } else {
                    try {
                        File[] files = inputFile.listFiles();
                        assert files != null;
                        for (File file : files) {
                            zipFile(file, ouputStream);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new MyException(CodeMsg.ZIP_FILE_PACKAGE_ERROR);
                    }
                }
            }
        } catch (Exception e) {
            throw new MyException(CodeMsg.ZIP_FILE_PACKAGE_ERROR);
        }
    }

}
