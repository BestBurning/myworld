package com.diyishuai.cloud.alibaba.consumer.controller;

import com.diyishuai.cloud.alibaba.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author: Bruce
 * @date: 2019-11-25
 * @description:
 */
@RestController
@RequestMapping("/consumer/template/user")
public class ConsumerRestTemplateController {

    private static final String URL_PREFIX = "http://aliprovider-user";

    @Autowired
    RestTemplate restTemplate;

    @PostMapping
    public boolean add(@RequestBody User user)
    {
        return restTemplate.postForObject(URL_PREFIX+"/user",user,Boolean.class);
    }

    @GetMapping(path = "/{id}")
    public User getById(@PathVariable("id") Long id)
    {
        return restTemplate.getForObject(URL_PREFIX+"/user/"+id,User.class);
    }

    @GetMapping(value = "/list")
    public List<User> list() {
        return restTemplate.getForObject(URL_PREFIX+"/user/list",List.class);
    }


}
