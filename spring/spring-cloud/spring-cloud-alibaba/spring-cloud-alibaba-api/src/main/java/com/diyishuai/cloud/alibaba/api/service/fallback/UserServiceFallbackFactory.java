package com.diyishuai.cloud.alibaba.api.service.fallback;

import com.diyishuai.cloud.alibaba.api.service.UserService;
import com.diyishuai.cloud.alibaba.api.model.User;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Bruce
 * @date: 2019-11-23
 * @description:
 */
@Component
public class UserServiceFallbackFactory implements FallbackFactory<UserService> {
    @Override
    public UserService create(Throwable throwable) {
        return new UserService() {
            @Override
            public boolean save(User user) {
                return false;
            }

            @Override
            public User getById(Long id) {
                return new User();
            }

            @Override
            public List<User> list() {
                return new ArrayList<User>(){};
            }
        };
    }

}
