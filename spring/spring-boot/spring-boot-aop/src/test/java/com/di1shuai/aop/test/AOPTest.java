package com.di1shuai.aop.test;

import com.di1shuai.aop.service.DoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: Shea
 * @date: 2020/6/24
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AOPTest {

    @Autowired
    DoService doService;

    @Test
    public void testAop() {
        String res = doService.hello("张三");
        System.out.println(res);
        Assert.assertTrue(res.equals("hello 张三"));
    }

}
