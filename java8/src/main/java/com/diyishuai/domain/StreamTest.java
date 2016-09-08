package com.diyishuai.domain;

import java.io.Serializable;

/**
 * Created by Bruce on 16/8/9.
 */
public class StreamTest implements Serializable{

    private Integer id;

    private Integer num;

    private Integer num2;

    private String service;

    private double result;



    public StreamTest() {
    }

    public StreamTest(Integer id, Integer num, Integer num2, String service) {
        this.id = id;
        this.num = num;
        this.num2 = num2;
        this.service = service;
    }

    public StreamTest(Integer id, Integer num, Integer num2) {
        this.id = id;
        this.num = num;
        this.num2 = num2;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getNum2() {
        return num2;
    }

    public void setNum2(Integer num2) {
        this.num2 = num2;
    }
}
