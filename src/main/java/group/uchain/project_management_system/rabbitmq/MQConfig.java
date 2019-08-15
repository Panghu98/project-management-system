package group.uchain.project_management_system.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author project_management_system
 * @title: MQConfig
 * @projectName project_management_system
 * @date 19-7-12 下午9:49
 */
@Configuration
public class MQConfig {

    static final String TOPIC_QUEUE = "topic.queue1";

    static final String TOPIC_EXCHANGE = "topicExchange";

    /**
     * Topic模式 交换机Exchange
     * 开启持久化
     * */

    @Bean
    public Queue topicQueue1(){
        return new Queue(TOPIC_QUEUE,true);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.#");
    }

}
