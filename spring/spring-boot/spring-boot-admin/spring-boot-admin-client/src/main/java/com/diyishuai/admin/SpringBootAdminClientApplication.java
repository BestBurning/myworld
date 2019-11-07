package com.diyishuai.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Bruce
 * @date: 2019-11-07
 * @description:
 */
@RestController
@SpringBootApplication
public class SpringBootAdminClientApplication {

    @GetMapping(path = "/hello")
    public String hello(){
        return "Hello SpringBootAdmin";
    }


    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminClientApplication.class);
    }
}
