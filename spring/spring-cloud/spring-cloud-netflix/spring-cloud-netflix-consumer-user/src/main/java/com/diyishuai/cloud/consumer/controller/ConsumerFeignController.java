package com.diyishuai.cloud.consumer.controller;

import com.diyishuai.cloud.api.model.User;
import com.diyishuai.cloud.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Bruce
 * @date: 2019-11-23
 * @description:
 */
@RestController
@RequestMapping(path = "/consumer/feign/user")
public class ConsumerFeignController {

    @Autowired
    UserService userService;

    @PostMapping
    public boolean save(@RequestBody User user)
    {
        return userService.save(user);
    }

    @GetMapping(path = "/{id}")
    public User getById(@PathVariable("id") Long id)
    {
        return userService.getById(id);
    }

    @GetMapping(value = "/list")
    public List<User> list() {
        return userService.list();
    }



}
