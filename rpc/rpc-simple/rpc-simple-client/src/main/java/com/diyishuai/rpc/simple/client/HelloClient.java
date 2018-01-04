package com.diyishuai.rpc.simple.client;

import com.diyishuai.rpc.client.RpcProxy;
import com.diyishuai.rpc.registry.ServiceDiscover;
import com.diyishuai.rpc.simple.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.diyishuai.rpc.simple.client")
public class HelloClient {
    private static Logger logger = LoggerFactory.getLogger(HelloClient.class);

    private static final String zk = "server01:2181";

//    private static final String client = "192.168.0.26:30008";

    @Bean
    public RpcProxy rpcProxy(){
        return new RpcProxy(new ServiceDiscover(zk));
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(HelloClient.class);
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names){
            logger.debug(name);
        }

        RpcProxy rpcProxy = applicationContext.getBean(RpcProxy.class);
        HelloService helloService = rpcProxy.create(HelloService.class);
        String result = helloService.hello("world");
        logger.info("client get result from server: " + result);

    }



}
