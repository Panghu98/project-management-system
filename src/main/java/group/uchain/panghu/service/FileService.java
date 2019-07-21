package group.uchain.panghu.service;

import group.uchain.panghu.result.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * @author panghu
 * @title: FileService
 * @projectName panghu
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

}
