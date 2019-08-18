package group.uchain.project_management_system.service;

import group.uchain.project_management_system.result.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author project_management_system
 * @title: FileService
 * @projectName project_management_system
 * @date 19-7-12 下午8:23
 */
public interface FileService {

    Result uploadFile(MultipartFile file);

    Result registerByExcel(MultipartFile file);

    /**
     *
     * @param files  文件名列表
     * @param response
     * @return
     */
    HttpServletResponse downloadZipFile(List<String> files, HttpServletResponse response);

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

    Result getAllocationExcel(Date startDate,Date endDate, HttpServletResponse response);
}
