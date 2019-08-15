package group.uchain.project_management_system.rabbitmq;

import group.uchain.project_management_system.dto.LoginInfo;
import group.uchain.project_management_system.mapper.LoginInfoMapper;
import group.uchain.project_management_system.util.TypeConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author project_management_system
 * @title: MQReceiver
 * @projectName project_management_system
 * @date 19-7-13 上午8:28
 */
@Slf4j
@Service
public class MQReceiver {

    private LoginInfoMapper loginInfoMapper;

    @Autowired
    public MQReceiver(LoginInfoMapper loginInfoMapper) {
        this.loginInfoMapper = loginInfoMapper;
    }

    /**
     * 登录消息入库进行操作
     * @param message
     */
    @RabbitListener(queues = MQConfig.TOPIC_QUEUE)
    public void receiveTopic(String message){

        LoginInfo info = TypeConvertUtil.stringToBean(message,LoginInfo.class);
        log.info("用户"+info.getUserId()+"在"+info.getDate()+"进行登录操作  "+"ip为"+info.getIp());
        loginInfoMapper.insert(info);
    }

}