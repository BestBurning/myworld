package com.diyishuai.boot.security.quick;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author: Bruce
 * @date: 2020/5/8
 * @description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = QuickSecurityApplication.class)
public class PasswordTest {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void test(){
        String pass = "123456";
        String hashPass = bCryptPasswordEncoder.encode(pass);
        System.out.println(hashPass);

        boolean f = bCryptPasswordEncoder.matches("123456",hashPass);
        Assert.assertTrue(f);
    }


}
