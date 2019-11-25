package com.diyishuai.cloud.alibaba.api.service;

import com.diyishuai.cloud.alibaba.api.model.User;

import java.util.List;

/**
 * @author: Bruce
 * @date: 2019-11-25
 * @description:
 */
public interface UserService {

    boolean save(User user);

    User getById(Long id);

    List<User> list();

}
