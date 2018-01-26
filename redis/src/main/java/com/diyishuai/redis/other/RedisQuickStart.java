package com.diyishuai.redis.other;

import redis.clients.jedis.Jedis;

/**
 * Describe: 请补充类描述
 */
public class RedisQuickStart {

    public static void main(String[] args) {
        // 根据redis主机和端口号实例化Jedis对象
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 添加key-value对象，如果key对象存在就覆盖该对象
        jedis.set("name", "maoxiangyi");
        // 查取key的value值，如果key不存在返回null
        String name = jedis.get("name");
        String company = jedis.get("company");
        System.out.println(company+":"+name);
        // 删除key-value对象，如果key不存在则忽略此操作
        jedis.del("name");
        // 判断key是否存在，不存在返回false存在返回true
        jedis.exists("name");
    }
}
