package com.diyishuai.threadpool.service;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class Task {

    public static Random random = new Random();

    @Autowired
    Task2 task2;

//    @Async("taskExecutor")
    public void doTaskOne() throws Exception {
//        log.info("开始做任务一");
//        long start = System.currentTimeMillis();
//        Thread.sleep(random.nextInt(10000));
//        long end = System.currentTimeMillis();
//        log.info("完成任务一，耗时：" + (end - start) + "毫秒");
       task2.doTaskTwo();
       task2.doTaskTwo();
    }

    @Async("taskExecutor")
    public void doTaskTwo() throws Exception {
        log.info("开始做任务二");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("完成任务二，耗时：" + (end - start) + "毫秒");
    }

    @Async("taskExecutor")
    public void doTaskThree() throws Exception {
        log.info("开始做任务三");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("完成任务三，耗时：" + (end - start) + "毫秒");
    }

}