package com.di1shuai.aop.service;

import com.di1shuai.aop.annotation.Log;
import org.springframework.stereotype.Service;

/**
 * @author: Shea
 * @date: 2020/6/24
 * @description:
 */
@Service
public class DoService {

    @Log(value = "test")
    public String hello(String name) {
        return "hello " + name;
    }

}
