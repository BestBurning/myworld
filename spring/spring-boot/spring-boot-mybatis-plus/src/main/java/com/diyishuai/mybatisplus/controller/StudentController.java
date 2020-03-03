package com.diyishuai.mybatisplus.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diyishuai.mybatisplus.domain.Student;
import com.diyishuai.mybatisplus.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author: Bruce
 * @date: 2020/2/20
 * @description:
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/{id}")
    public Student getById(@PathVariable Integer id){
        return studentService.getById(id);
    }

    @GetMapping
    public Page<Student> get(Integer current,Integer size){
        return studentService.page(new Page(current,size));
    }
    @PostMapping
    public boolean save(@RequestBody Student student){
        return studentService.save(student);
    }

    @PutMapping
    public boolean update(@RequestBody Student student){
        return studentService.updateById(student);
    }

    @DeleteMapping(path = "/{id}")
    public boolean delete(@PathVariable("id") Integer id){
        return studentService.removeById(id);
    }

}
