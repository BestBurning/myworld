package com.diyishuai.cloud.alibaba;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Bruce
 * @date: 2019-11-28
 * @description:
 */
@EnableAdminServer
@SpringBootApplication
public class AliAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AliAdminApplication.class,args);
    }


}
