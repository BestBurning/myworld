package com.diyishuai.redis;

import redis.clients.jedis.Jedis;

public class RedisMain {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        String response = jedis.ping();
        jedis.set("hello","world");
        System.out.println(response);
        System.out.println(jedis.get("hello"));
    }

}
