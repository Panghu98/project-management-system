package group.uchain.project.service;

import group.uchain.project.result.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author project
 * @title: FileService
 * @projectName project
 * @date 19-7-12 下午8:23
 */
public interface FileService {

    String REDIS_HASH_KEY = "project-info:";


    Result importProjectByExcel(MultipartFile file);

    Result registerByExcel(MultipartFile file);

    /**
     * 多文件下载 (其实这里也可以单文件打包下载)
     * @param files  文件名列表
     * @param response
     * @return
     */
    HttpServletResponse downloadZipFile(List<String> files, HttpServletResponse response);

    /**
     * 根据项目ID进行单文件下载
     * @param id 项目ID
     */
    void  downloadSingleFile(String id,HttpServletResponse response);

    /**
     * 获取证明材料的所有文件名
     * @return
     */
    Result getAllFilesName();

    /**
     * 上传证明材料接口
     * @return
     */
    Result uploadEvidentFile(MultipartFile multipartFile);

    /**
     * 根据文件导入时间导出分配信息的excel
     * @param startDate 开始
     * @param endDate 结束
     * @return 是否成功
     */
    void getAllocationExcel(Long startDate,Long endDate, HttpServletResponse response);

}
