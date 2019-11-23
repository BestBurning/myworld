package com.diyishuai.cloud.consumer.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Bruce
 * @date: 2019-11-23
 * @description:
 */
@Configuration
public class RobbinConfig {

    @Bean
    public IRule rule(){
        //轮训
        //  return new RoundRobinRule();
        //随机
        return new RandomRule();
    }

}
