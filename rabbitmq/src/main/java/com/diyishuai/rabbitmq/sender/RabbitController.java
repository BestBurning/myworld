package com.diyishuai.rabbitmq.sender;

import com.diyishuai.rabbitmq.sender.FanoutSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bruce
 * @since 2018/6/1
 */
@RestController
public class RabbitController {

    @Autowired
    FanoutSender fanoutSender;

    /**
     * fanout exchange类型rabbitmq测试
     */
    @PostMapping("/fanoutTest")
    public void fanoutTest() {
        fanoutSender.send();
    }

}
