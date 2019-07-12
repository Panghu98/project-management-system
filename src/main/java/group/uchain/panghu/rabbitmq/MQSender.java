package group.uchain.panghu.rabbitmq;

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

    public void sendLoginInfo(){

    }
}
