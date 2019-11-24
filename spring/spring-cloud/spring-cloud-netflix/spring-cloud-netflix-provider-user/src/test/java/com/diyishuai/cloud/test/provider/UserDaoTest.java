package com.diyishuai.cloud.test.provider;

import com.diyishuai.cloud.api.model.User;
import com.diyishuai.cloud.provider.ProviderApplication;
import com.diyishuai.cloud.provider.dao.UserDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: Bruce
 * @date: 2019-11-23
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProviderApplication.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userDao.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

}
