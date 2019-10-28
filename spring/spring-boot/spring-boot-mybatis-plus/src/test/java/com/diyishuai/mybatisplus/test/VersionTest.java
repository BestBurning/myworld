package com.diyishuai.mybatisplus.test;

import com.diyishuai.mybatisplus.MybtaisPlusApplication;
import com.diyishuai.mybatisplus.dao.StudentDao;
import com.diyishuai.mybatisplus.domain.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * @author Bruce
 * @since 2018/6/26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MybtaisPlusApplication.class)
public class VersionTest {

    @Autowired
    private StudentDao studentDao;

    @Test
    public void insertTest(){
        Student student = new Student();
        student.setAdmin(true);
        student.setName("hello");
        student.setVersion(1);
        studentDao.insert(student);

    }


    @Test
    public void versionTest(){
        Student student = new Student();
        student.setId(1);
        student.setName(UUID.randomUUID().toString());
        student.setAdmin(false);
        student.setVersion(4);
        studentDao.updateById(student);

    }

    @Test
    public void updateTest(){
        Student student = new Student();
        student.setId(1);
        student.setName("hehe");
        student.setAdmin(true);
        studentDao.updateById(student);

    }




}
