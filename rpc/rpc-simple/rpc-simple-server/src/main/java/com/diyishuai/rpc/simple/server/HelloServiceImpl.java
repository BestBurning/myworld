package com.diyishuai.rpc.simple.server;

import com.diyishuai.rpc.server.RpcService;
import com.diyishuai.rpc.simple.api.HelloService;

@RpcService("helloService")
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        String str = "hello " + name + " !";
        return str;
    }
}
