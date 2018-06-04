package com.diyishuai.rabbitmq.receive;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloReceive {

    @Autowired
    private FanoutExchange fanoutExchange;

    @RabbitListener(queues="#{queueA.name}")
    public void processA(String str1) {

        System.out.println("ReceiveA:"+str1);
    }
    @RabbitListener(queues="#{queueB.name}")
    public void processB(String str) {
        System.out.println("ReceiveB:"+str);
    }
//    @RabbitListener(queues="com.centrixlink.queue.status")
//    public void processC(String str) {
//        System.out.println("ReceiveC:"+str);
//    }


}