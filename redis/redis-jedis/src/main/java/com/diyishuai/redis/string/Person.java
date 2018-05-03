package com.diyishuai.redis.string;

import java.io.Serializable;

/**
 */
public class Person implements Serializable{
    private static final long serialVersionUID = -9012113097419111583L;
    private String name;//姓名
    private int age;//年龄

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
