package com.diyishuai.rabbitmq.receive2;

import org.springframework.amqp.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


/**
 * @author Bruce
 * @since 2018/5/18
 */

@SpringBootApplication
public class RabbitMQApplicationConsumer2 {

    static final String topicExchangeName = "amq.fanout";

    @Bean
    Queue queue() {
        return new AnonymousQueue();
    }


    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(topicExchangeName);
    }

    @Bean
    Binding bindingA(Queue queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(RabbitMQApplicationConsumer2.class, args);
    }

}
