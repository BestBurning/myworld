package com.diyishuai.boot.security.quick.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Bruce
 * @date: 2020/5/8
 * @description:
 */
@RestController
public class SignController {

    @PostMapping("/signin")
    public String signin(){
        return "ok";
    }

}
