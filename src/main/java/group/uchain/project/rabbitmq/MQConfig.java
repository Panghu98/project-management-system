package group.uchain.project.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author project
 * @title: MQConfig
 * @projectName project
 * @date 19-7-12 下午9:49
 */
@Configuration
public class MQConfig {

    static final String DIRECT_LOGIN = "direct.login";

    static final String DIRECT_PROJECT = "direct.project";

    static final String DIRECT_EXCHANGE = "directExchange";

    /**
     * Topic模式 交换机Exchange
     * 开启持久化
     * */

    @Bean
    public Queue directQueueLogin(){
        return new Queue(DIRECT_LOGIN,true);
    }

    @Bean
    public Queue directQueueProject(){
        return new Queue(DIRECT_PROJECT,true);
    }


    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public Binding directLoginBinding() {
        return BindingBuilder.bind(directQueueLogin()).to(directExchange()).with("direct.login");
    }

    @Bean
    Binding directProjectBinding(){
        return BindingBuilder.bind(directQueueProject()).to(directExchange()).with("direct.project");
    }



}
