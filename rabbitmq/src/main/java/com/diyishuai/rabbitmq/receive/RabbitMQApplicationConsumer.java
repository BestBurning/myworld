package com.diyishuai.rabbitmq.receive;

import org.springframework.amqp.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


/**
 * @author Bruce
 * @since 2018/5/18
 */

@SpringBootApplication
public class RabbitMQApplicationConsumer {

    static final String topicExchangeName = "amq.fanout";

    @Bean
    Queue queueA() {
        return new AnonymousQueue();
    }

    @Bean
    Queue queueB() {
        return new AnonymousQueue();
    }


    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(topicExchangeName);
    }

    @Bean
    Binding bindingA(Queue queueA, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueA).to(fanoutExchange);
    }

    @Bean
    Binding bindingB(Queue queueB, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueB).to(fanoutExchange);
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(RabbitMQApplicationConsumer.class, args);
    }

}
