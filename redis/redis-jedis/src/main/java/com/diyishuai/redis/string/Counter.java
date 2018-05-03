package com.diyishuai.redis.string;

import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Describe: 擂台比武
 */
public class Counter {
    /**
     * 计算 武林大会 三个擂台的比武次数
     *
     * @param args
     */
    public static void main(String[] args) {
        //创建一个固定大小的线程池，3个擂台
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //擂台1：天龙八部
        executorService.submit(new Arena("biwu:totalNum","天龙八部"));
        //擂台2：神雕侠侣
        executorService.submit(new Arena("biwu:totalNum","神雕侠侣"));
        //擂台3：倚天屠龙记
        executorService.submit(new Arena("biwu:totalNum","倚天屠龙记"));
        //报幕人员，一秒统计一次总共比了多少场
        executorService.submit(new BaoMu("biwu:totalNum"));
    }
}
