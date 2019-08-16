package group.uchain.project_management_system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 文件信息
 * @author panghu
 */
@Data
public class FileInfo {

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件最后修改时间")
    private Long date;

}
