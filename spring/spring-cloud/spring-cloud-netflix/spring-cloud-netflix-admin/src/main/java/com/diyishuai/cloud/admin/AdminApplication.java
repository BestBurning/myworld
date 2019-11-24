package com.diyishuai.cloud.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author: Bruce
 * @date: 2019-11-24
 * @description:
 */
@SpringBootApplication
@EnableEurekaClient
@EnableAdminServer
public class AdminApplication {

    public static void main(String[] args) {

        SpringApplication.run(AdminApplication.class,args);

    }

}
