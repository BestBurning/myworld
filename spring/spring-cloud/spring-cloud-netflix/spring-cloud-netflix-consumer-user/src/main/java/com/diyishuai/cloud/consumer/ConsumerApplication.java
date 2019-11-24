package com.diyishuai.cloud.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: Bruce
 * @date: 2019-11-23
 * @description:
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.diyishuai.cloud")
@EnableCircuitBreaker
@ComponentScan(basePackages = "com.diyishuai.cloud")
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }

}
