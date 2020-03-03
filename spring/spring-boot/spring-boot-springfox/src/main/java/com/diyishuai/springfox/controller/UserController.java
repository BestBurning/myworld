package com.diyishuai.springfox.controller;

import com.diyishuai.springfox.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Bruce
 * @date: 2020/2/22
 * @description:
 */
@Api(tags = {"用户相关接口"},value = "提供用户相关的 Rest API")
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation("通过Id得到单个用户")
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id){
        return new User()
                .setId(id)
                .setName("test-"+id)
                .setAge(20);
    }
    @ApiOperation("获取用户列表")
    @GetMapping
    public List<User> getList(){
        return new ArrayList<User>(Arrays.asList(
                new User()
                .setId(1l)
                .setName("test1")
                .setAge(20),
                new User()
                .setId(2l)
                .setName("test2")
                .setAge(22)
                ));
    }

    @ApiOperation("新增用户接口")
    @PostMapping
    public boolean save(@RequestBody User user){
        return true;
    }

    @ApiOperation("用户更新接口")
    @PutMapping
    public boolean update(@RequestBody User user){
        return true;
    }

    @ApiIgnore
    @ApiOperation("删除用户接口")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id){
        return true;
    }

}
