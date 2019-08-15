package group.uchain.project_management_system.rabbitmq;

import group.uchain.project_management_system.dto.LoginInfo;
import group.uchain.project_management_system.util.TypeConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author project_management_system
 * @title: MQSender
 * @projectName project_management_system
 * @date 19-7-12 下午9:56
 */
@Service
@Slf4j
public class MQSender {

    private AmqpTemplate amqpTemplate;

    @Autowired
    public MQSender(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendLoginInfo(LoginInfo loginInfo){
        String msg = TypeConvertUtil.beanToString(loginInfo);
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,"topic.key1",msg);
    }
}
