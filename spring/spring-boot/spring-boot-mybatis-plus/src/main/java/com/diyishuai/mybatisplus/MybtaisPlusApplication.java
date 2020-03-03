package com.diyishuai.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Bruce
 * @since 2018/6/26
 */
@SpringBootApplication
@MapperScan("com.diyishuai.mybatisplus.dao")
public class MybtaisPlusApplication {

    public static void main(String[] args) {
        
        SpringApplication.run(MybtaisPlusApplication.class,args);
    }

}
