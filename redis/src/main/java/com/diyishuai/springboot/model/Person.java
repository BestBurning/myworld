package com.diyishuai.springboot.model;

import java.io.Serializable;

/**
 * Created by Bruce on 16/7/21.
 */
public class Person implements Serializable{

    private String id;

    private String name;

    private Integer age;

    public Person() {
    }

    public Person(String id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
