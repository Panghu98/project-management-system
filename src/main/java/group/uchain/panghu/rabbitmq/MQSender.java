package group.uchain.panghu.rabbitmq;

import group.uchain.panghu.dto.LoginInfo;
import group.uchain.panghu.util.TypeConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author panghu
 * @title: MQSender
 * @projectName panghu
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
