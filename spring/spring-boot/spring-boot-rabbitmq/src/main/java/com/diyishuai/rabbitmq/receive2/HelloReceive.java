package com.diyishuai.rabbitmq.receive2;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloReceive {

    @Autowired
    private FanoutExchange fanoutExchange;

    @RabbitListener(queues="#{queue.name}")
    public void processA(String str1) {

        System.out.println("Receive:"+str1);
    }
}