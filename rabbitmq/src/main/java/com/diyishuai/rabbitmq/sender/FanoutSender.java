package com.diyishuai.rabbitmq.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Bruce
 * @since 2018/6/1
 */
@Component
public class FanoutSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private FanoutExchange fanoutExchange;

    public void send() {
        String msgString="fanoutSender :hello i am hzb";
        System.out.println(msgString);
        this.rabbitTemplate.convertAndSend(fanoutExchange.getName(),null, msgString);
    }

}