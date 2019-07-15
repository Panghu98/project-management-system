package group.uchain.panghu.service;

import group.uchain.panghu.result.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author panghu
 * @title: FileService
 * @projectName panghu
 * @date 19-7-12 下午8:23
 */
public interface FileService {

    Result uploadFile(MultipartFile file);

    Result registerByExcel(MultipartFile file);

}
