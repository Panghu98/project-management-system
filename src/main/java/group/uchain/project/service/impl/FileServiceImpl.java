package group.uchain.project.service.impl;

import group.uchain.project.entity.RegisterUser;
import group.uchain.project.dto.ProjectInfo;
import group.uchain.project.dto.User;
import group.uchain.project.enums.CodeMsg;
import group.uchain.project.exception.MyException;
import group.uchain.project.mapper.AllocationInfoMapper;
import group.uchain.project.mapper.FileInfoMapper;
import group.uchain.project.mapper.ProjectInfoMapper;
import group.uchain.project.mapper.UserFormMapper;
import group.uchain.project.rabbitmq.MQSender;
import group.uchain.project.result.Result;
import group.uchain.project.service.FileService;
import group.uchain.project.util.ExcelUtil;
import group.uchain.project.vo.AllocationInfo2;
import group.uchain.project.vo.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author project
 * @title: FileServiceImpl
 * @projectName project
 * @date 19-7-12 下午8:24
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private UserFormMapper userFormMapper;

    private ProjectInfoMapper projectInfoMapper;

    private AllocationInfoMapper allocationInfoMapper;

    private MQSender mqSender;

    private RedisTemplate redisTemplate;

    private FileInfoMapper fileInfoMapper;


    @Autowired
    public FileServiceImpl(ProjectInfoMapper projectInfoMapper, UserFormMapper userFormMapper,
                           MQSender mqSender, AllocationInfoMapper allocationInfoMapper,
                           RedisTemplate redisTemplate,FileInfoMapper fileInfoMapper) {

        this.projectInfoMapper = projectInfoMapper;
        this.userFormMapper = userFormMapper;
        this.mqSender = mqSender;
        this.allocationInfoMapper = allocationInfoMapper;
        this.redisTemplate = redisTemplate;
        this.fileInfoMapper = fileInfoMapper;
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
     * 通过导入Excel查询导入数据库 分配项目信息
     * @param file  项目文件
     * @return
     */
    @Override
    public Result importProjectByExcel(MultipartFile file) {
        if (file==null){
            throw new MyException(CodeMsg.FILE_EMPTY_ERROR);
        }

        if (!file.getOriginalFilename().contains(".xlsx")){
            System.err.println(file.getName());
            throw new MyException(CodeMsg.FILE_TYPE_ERROR);
        }
        String pathFile = projectFilePath+file.getOriginalFilename();

        try{


            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathFile);
            Files.write(path,bytes);


        } catch (IOException e) {

            log.error("项目信息文档上传失败");
            e.printStackTrace();

            throw new MyException(CodeMsg.FILE_UPLOAD_FAILED);
        }

        log.info("文件上传成功");
        //读取Excel表格 存入数据库
        List<ProjectInfo> list = ExcelUtil.importProjectXLS(pathFile);
        List<String> listOfId = new ArrayList<>();
        for (ProjectInfo projectInfo : list) {
            String id = projectInfo.getId();
            listOfId.add(id);
        }
        List<String> idList = projectInfoMapper.getRepeatNums(listOfId);
        if(idList.size()!=0){
            return Result.error(20,"项目编号"+idList.toString()+"已经存在");
        }

        //放入消息队列,插入数据库
        log.info("异步发送消息");
        mqSender.sendProjectInfo(list);

        //将导入的数据放入缓存,并且通过redis标记数据库更新
        Map<String, Object> map = list.stream().collect(Collectors.toMap(ProjectInfo::getId,(p)->p));
        redisTemplate.opsForHash().putAll(REDIS_HASH_KEY,map);
        return new Result<>(list);

    }

    @Override
    public Result registerByExcel(MultipartFile file) {
        if (file==null){
            //自定义异常类
            throw new MyException(CodeMsg.FILE_EMPTY_ERROR);
        }

        //判断文件后缀名
        if (!file.getContentType().contains(".xlsx")){
            throw new MyException(CodeMsg.FILE_TYPE_ERROR);
        }

        String pathFile = registerFilePath+file.getOriginalFilename();

        try{
            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathFile);
            Files.write(path,bytes);

        } catch (IOException e) {

            e.printStackTrace();
            log.error("注册文档上传失败");
            throw new MyException(CodeMsg.FILE_UPLOAD_FAILED);
        }

        //读取Excel表格存入数据库
        List<RegisterUser> list = ExcelUtil.getUsersByExcel(pathFile);
        //真正用于注册的用户集合
        List<User> users = new ArrayList<>();
        //记录注册用户的ID,用户判断用户是否已经存在
        List<Long> idList = new ArrayList<>();
        for (RegisterUser registerUser:list
             ) {
            idList.add(registerUser.getUserId());
        }
        //获取重复ID集合
        List<Long> repeatIdList = userFormMapper.getRepeatUserId(idList);
        if (repeatIdList.size() != 0){
            log.error("项目编号"+repeatIdList.toString()+"已经存在");
            return Result.error(20,"用户工号"+repeatIdList.toString()+"已经存在");
        }else {
            for (RegisterUser registerUser:list
            ) {
                User user = new User(registerUser);
                users.add(user);
            }
        }
        //注册用户入库
        userFormMapper.registerMultiUser(users);
        return Result.successData(users);

    }

    @Override
    public HttpServletResponse downloadZipFile(List<String> projectIdList, HttpServletResponse response) {
        List<String> filenameList = fileInfoMapper.getCompleteFileNameListByProjectId(projectIdList);
        List<File> fileList = new ArrayList<>();
        for (String fileName:filenameList
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

    @Override
    public void downloadSingleFile(String id,HttpServletResponse response) {

        String fileName = fileInfoMapper.getCompleteFileNameByProjectId(id);
        if (fileName == null){
            throw new MyException(CodeMsg.ID_HAS_NO_FILE);
        }
        File file = new File(evidentFilePath+fileName);
        if(file.exists()){
            response.setContentType("application/force-download");
            response.setHeader("name",fileName);
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);

            byte[] buffer = new byte[1024];
            //文件输入流
            FileInputStream fis = null;
            BufferedInputStream bis = null;

            //输出流
            OutputStream os = null;
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            log.info("文件下载中........");
            try {
                assert bis != null;
                bis.close();
                fis.close();
                assert os != null;
                os.close();
            } catch (IOException e) {
                log.error("error:{}",e.getMessage());
            }
        }
        log.info("文件下载完成");
    }


    @Override
    public Result getAllFilesName() {
        List<FileInfo> list = new ArrayList<>();
        File file = new File(evidentFilePath);

        File[] fileList = file.listFiles();
        if (fileList==null){
            throw new MyException(CodeMsg.FILE_IS_EMPTY);
        }

        //遍历文件 返回文件的最后修改时间和文件名称
        for (File value : fileList) {
            if (value.isFile()) {
                String fileName = value.getName();
                Long date = value.lastModified();
                FileInfo fileInfo = new FileInfo();
                fileInfo.setDate(date);
                fileInfo.setFileName(fileName);
                list.add(fileInfo);
            }
        }
        return Result.successData(list);
    }

    @Override
    public Result uploadEvidentFile(MultipartFile file) {
        if (file==null){
            throw new MyException(CodeMsg.FILE_EMPTY_ERROR);
        }

        String pathFile = evidentFilePath+file.getOriginalFilename();

        try{
            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathFile);
            Files.write(path,bytes);

        } catch (IOException e) {

            e.printStackTrace();
            log.error("证明材料上传失败");
            throw new MyException(CodeMsg.FILE_UPLOAD_FAILED);
        }
        return new Result();
    }

    /**
     * 导出Excel 项目分配信息
     * @param endDate Excel中项目信息的开始时间
     * @param startDate  Excel中项目信息的结束时间
     * @param response
     */
    @Override
    public void getAllocationExcel(Long startDate, Long endDate, HttpServletResponse response) {
        Date start = new Date(startDate);
        Date end = new Date(endDate);
        //管理员获取到所有的项目分配信息
        List<AllocationInfo2> list = allocationInfoMapper.getAllAllocationInfo(start,end);
        // 1.创建HSSFWorkbook，一个HSSFWorkbook对应一个Excel文件
        XSSFWorkbook wb = new XSSFWorkbook();
        // 2.在workbook中添加一个sheet,对应Excel文件中的sheet(工作栏)
        XSSFSheet sheet = wb.createSheet("sheet1");
        //3.设置格式  基本单位是1/256个字符
        //序号
        sheet.setColumnWidth(0,256*10);
        //项目编号
        sheet.setColumnWidth(1,256*14);
        //项目类别
        sheet.setColumnWidth(2,256*17);
        //项目说明
        sheet.setColumnWidth(3,256*50);
        //级别
        sheet.setColumnWidth(4,256*10);
        //等级
        sheet.setColumnWidth(5,256*10);
        //项数
        sheet.setColumnWidth(6,256*10);
        //分数类型
        sheet.setColumnWidth(7,256*10);
        //学校认定分数（暂定）
        sheet.setColumnWidth(8,256*12);
        //负责人
        sheet.setColumnWidth(9,256*10);
        //分配信息
        sheet.setColumnWidth(10,256*100);
        //备注
        sheet.setColumnWidth(11,256*50);

        //3.1设置字体居中
        XSSFCellStyle cellStyle = wb.createCellStyle();
        //自动换行
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        // 4.设置表头，即每个列的列名
        String[] title = {"项目编号","项目类别","项目说明","级别","等级","项数","分数类型","分数（暂定）","项目负责人","划分信息","备注"};
        // 4.1创建第一行
        XSSFRow row = sheet.createRow(0);
        // 此处创建一个序号列
        row.createCell(0).setCellValue("序号");
        for (int i = 0; i < title.length; i++) {
            // 给列写入数据,创建单元格，写入数据
            row.createCell(i+1).setCellValue(title[i]);
        }
        //写入正式数据
        for (int i = 0; i < list.size(); i++) {
            //创建行
            // 创建行
            row = sheet.createRow(i+1);

            //设置行高
            row.setHeight((short) (16*60));
            // 序号
            row.createCell(0).setCellValue(i+1);
            row.createCell(1).setCellValue(list.get(i).getProjectId());
            row.createCell(2).setCellValue(list.get(i).getCategory());
            row.createCell(3).setCellValue(list.get(i).getInstruction());
            row.createCell(4).setCellValue(list.get(i).getLevel());
            row.createCell(5).setCellValue(list.get(i).getGrade());
            row.createCell(6).setCellValue(list.get(i).getNumber());
            row.createCell(7).setCellValue(list.get(i).getVariety());
            row.createCell(8).setCellValue(list.get(i).getScore());
            row.createCell(9).setCellValue(list.get(i).getLeader());

            String teachers = list.get(i).getTeacherInfos().toString().replaceAll(", ","\r\n");
            //去除首尾的[]
            teachers = teachers.substring(1,teachers.length()-1);
            row.createCell(10).setCellValue(teachers);
        }



        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=demo.xlsx");
        try {
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        }catch (IOException e){
            throw new MyException(CodeMsg.FILE_DOWNLOAD_ERROR);
        }
    }






    /*
        -----------------------------------问价压缩下载的私有方法-------------------------------
     */

    private static HttpServletResponse downloadZip(File file, HttpServletResponse response) {
        if (!file.exists()) {
            System.out.println("待压缩的文件目录：" + file + "不存在.");
        } else {
            try {
                // 以流的形式下载文件。
                log.info("进入压缩文件下载环节");
                InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
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
            }  finally {
                try {
                    File f = new File(file.getPath());
                    f.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
    private static void zipFile(File inputFile, ZipOutputStream outputStream) {
        try {
            if (inputFile.exists()) {
                if (inputFile.isFile()) {
                    FileInputStream fileInputStream = new FileInputStream(inputFile);
                    BufferedInputStream bins = new BufferedInputStream(fileInputStream, 512);
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    outputStream.putNextEntry(entry);
                    // 向压缩文件中输出数据
                    int nNumber;
                    byte[] buffer = new byte[512];
                    while ((nNumber = bins.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, nNumber);
                    }
                    // 关闭创建的流对象
                    bins.close();
                    fileInputStream.close();
                } else {
                    try {
                        File[] files = inputFile.listFiles();
                        assert files != null;
                        for (File file : files) {
                            zipFile(file, outputStream);
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
