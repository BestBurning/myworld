package com.diyishuai.cloud.alibaba.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: Bruce
 * @date: 2019-11-25
 * @description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.diyishuai.cloud.alibaba.provider.dao")
public class AliProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AliProviderApplication.class,args);
    }

}
