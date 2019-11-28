package com.diyishuai.cloud.alibaba.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: Bruce
 * @date: 2019-11-25
 * @description:
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.diyishuai.cloud.alibaba")
@ComponentScan("com.diyishuai.cloud.alibaba")
public class AliConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AliConsumerApplication.class,args);
    }

}
