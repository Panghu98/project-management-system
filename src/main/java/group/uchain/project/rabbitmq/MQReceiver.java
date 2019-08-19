package group.uchain.project.rabbitmq;

import com.alibaba.fastjson.JSONArray;
import group.uchain.project.dto.LoginInfo;
import group.uchain.project.dto.ProjectInfo;
import group.uchain.project.mapper.LoginInfoMapper;
import group.uchain.project.mapper.ProjectInfoMapper;
import group.uchain.project.redis.RedisUtil;
import group.uchain.project.util.TypeConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author project
 * @title: MQReceiver
 * @projectName project
 * @date 19-7-13 上午8:28
 */
@Slf4j
@Service
public class MQReceiver {

    private LoginInfoMapper loginInfoMapper;

    private ProjectInfoMapper projectInfoMapper;

    private RedisUtil redisUtil;


    @Autowired
    public MQReceiver(LoginInfoMapper loginInfoMapper,ProjectInfoMapper projectInfoMapper,
                      RedisUtil redisUtil) {
        this.loginInfoMapper = loginInfoMapper;
        this.projectInfoMapper = projectInfoMapper;
        this.redisUtil = redisUtil;
    }

    /**
     * 登录消息入库进行操作
     * @param message
     */
    @RabbitListener(queues = MQConfig.DIRECT_LOGIN)
    public void receiveLoginMessage(String message){
        LoginInfo info = TypeConvertUtil.stringToBean(message,LoginInfo.class);
        log.info("用户"+info.getUserId()+"在"+info.getDate()+"进行登录操作  "+"ip为"+info.getIp());
        loginInfoMapper.insert(info);
    }

    /**
     * 文件注册数据异步入库
     */
    @RabbitListener(queues = MQConfig.DIRECT_PROJECT )
    public void receiveProjectMessage(String message){
        List<ProjectInfo> list = JSONArray.parseArray(message,ProjectInfo.class);
        projectInfoMapper.excelToDatabase(list);
        //标记数据库是否更新
        redisUtil.set("project-info-flag","Y");
    }

}
