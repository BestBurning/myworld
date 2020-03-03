package com.diyishuai.mybatisplus.test;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diyishuai.mybatisplus.MybtaisPlusApplication;
import com.diyishuai.mybatisplus.dao.StudentDao;
import com.diyishuai.mybatisplus.domain.Student;
import com.diyishuai.mybatisplus.service.StudentService;
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

    @Autowired
    private StudentService studentService;

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
        student.isAdmin();
        studentDao.updateById(student);

    }

    @Test
    public void selectTest(){
        studentDao.selectList(null).forEach(s -> System.out.println(s));
    }

    @Test
    public void pageTest(){
        studentService.page(new Page<Student>().setCurrent(1).setSize(4))
        .getRecords().forEach(s -> System.out.println(s));
    }




}
