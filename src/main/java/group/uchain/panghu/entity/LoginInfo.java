package group.uchain.panghu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author panghu
 * @title: LoginInfo
 * @projectName panghu
 * @date 19-7-12 下午9:59
 */
@Data
public class LoginInfo {

    private Long userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;



}
