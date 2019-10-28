package com.diyishuai.rabbitmq.sender;

import org.springframework.amqp.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


/**
 * @author Bruce
 * @since 2018/5/18
 */

@SpringBootApplication
public class RabbitMQApplicationProducer {

    static final String topicExchangeName = "amq.fanout";

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(topicExchangeName);
    }



    public static void main(String[] args) {
        SpringApplication.run(RabbitMQApplicationProducer.class, args);
    }

}
