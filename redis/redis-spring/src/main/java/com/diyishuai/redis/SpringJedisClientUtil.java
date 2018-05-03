package com.diyishuai.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

//@Configuration
public class SpringJedisClientUtil {
    @Value("{spring.redis.host}")
    private static String host;
    @Value("{spring.redis.port}")
    private static Integer port;
    private final static byte[] temp_lock = new byte[1];
    private static JedisPool jedisPool;
    /**
     * 把连接池做成单例的，这点需要注意
     *
     * @return
     */
    @Bean
    public static JedisPool getJedisPool() {
        if (jedisPool == null) {
            synchronized (temp_lock) {if (jedisPool == null) {
                jedisPool = new JedisPool(jedisPoolConfig(),host,port);
            }
            }
        }
        return jedisPool;
    }
    /**
     * 设置⼀些连接池的配置，来管理每⼀个连接。
     *
     * @return
     */
    @Bean
    public static JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);//最⼤连接池数
        jedisPoolConfig.setMaxIdle(10);//最⼤空闲数
        jedisPoolConfig.setMaxWaitMillis(1000);//最⻓等待时间
        return jedisPoolConfig;
    }
    /**
     * 对外只暴露这⼀个⽅法即可
     *
     * @return
     */
    @Bean
    public static Jedis getJedis(){
        return getJedisPool().getResource();
    }

    public static void main(String[] args) {
        Jedis jedis = SpringJedisClientUtil.getJedis();
        try {
            jedis.set("foot", "bar");
            String value = jedis.get("foot");
            System.out.println(value);
        } finally {
            //注意关闭
            jedis.close();
        }
    }


}
