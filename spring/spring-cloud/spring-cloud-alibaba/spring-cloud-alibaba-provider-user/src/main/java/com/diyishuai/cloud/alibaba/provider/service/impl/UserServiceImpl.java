package com.diyishuai.cloud.alibaba.provider.service.impl;


import com.diyishuai.cloud.alibaba.api.model.User;
import com.diyishuai.cloud.alibaba.api.service.UserService;
import com.diyishuai.cloud.alibaba.provider.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Bruce
 * @date: 2019-11-22
 * @description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public boolean save(User user) {
        return userDao.insert(user) > 0 ? true : false;
    }

    @Override
    public User getById(Long id) {
        return userDao.selectById(id);
    }

    @Override
    public List<User> list() {
        return userDao.selectList(null);
    }
}
