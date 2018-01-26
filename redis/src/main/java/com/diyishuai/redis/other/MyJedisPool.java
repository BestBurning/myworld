package com.diyishuai.redis.other;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Describe: 请补充类描述
 */
public class MyJedisPool {
    // jedis池
    public static JedisPool pool;

    // 静态代码初始化池配置
    static {
        //change "maxActive" -> "maxTotal" and "maxWait" -> "maxWaitMillis" in all examples
        JedisPoolConfig config = new JedisPoolConfig();
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(5);
        //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
        //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        config.setMaxTotal(1000 * 100);
        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        config.setMaxWaitMillis(5);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        try {
            //如果你遇到 java.net.SocketTimeoutException: Read timed out exception的异常信息
            //请尝试在构造JedisPool的时候设置自己的超时值. JedisPool默认的超时时间是2秒(单位毫秒)
            pool = new JedisPool(config, "127.0.0.1", 6379, 20);
        } catch (Exception e) {
            throw new RuntimeException("redis 连接池初始化失败！");
        }
    }

    public static void main(String[] args) {
        // 从jedis池中获取一个jedis实例
        Jedis jedis = MyJedisPool.pool.getResource();
        // 添加key-value对象，如果key对象存在就覆盖该对象
        jedis.set("name", "maoxiangyi");
        jedis.set("company", "aaa");
        // 查取key的value值，如果key不存在返回null
        String name = jedis.get("name");
        String company = jedis.get("company");
        System.out.println(company + ":" + name);
        // 删除key-value对象，如果key不存在则忽略此操作
        jedis.del("name");
        // 判断key是否存在，不存在返回false存在返回true
        jedis.exists("name");
        //关闭jedis链接，自动回收
        jedis.close();
    }
}
