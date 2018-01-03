package com.diyishuai.spring.annotation;

@RpcService("helloService")
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "hello "+ name + " !";
    }

}
