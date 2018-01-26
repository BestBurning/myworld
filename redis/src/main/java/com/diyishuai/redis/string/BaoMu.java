package com.diyishuai.redis.string;

import redis.clients.jedis.Jedis;

import java.util.concurrent.Callable;

/**
 * Describe: 报幕人员
 */
public class BaoMu implements Runnable {
    private Jedis jedis;
    private String redisKey;

    public BaoMu(String redisKey) {
        this.redisKey = redisKey;
    }

    public void run() {
        jedis = new Jedis("127.0.0.1",6379);
        while (true) {
            try {
                Thread.sleep(1000);
                System.out.println("===================当前总共比武次数为：" + jedis.get(redisKey));
            } catch (Exception e) {
                System.out.println("擂台被损坏..."+e);
            }
        }
    }
}
