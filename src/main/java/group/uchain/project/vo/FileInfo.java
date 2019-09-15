package group.uchain.project.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
