package com.diyishuai.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import redis.clients.jedis.Jedis;

/**
 * Created By jack on 16/12/2017
 * 单线程环境下使⽤，简单Util
 * 正常正式开发中，会把Jedis包装在⼀个单利模式中，避免每次都去重新连接，把localhost和port放到p
 * roperties的配置⽂件中
 **/
//@SpringBootApplication
public class JedisClientUtil {
    @Value("{spring.redis.host}")
    private static String host;
    @Value("{spring.redis.port}")
    private static Integer port;
    private final static byte[] temp_lock = new byte[1];
    private static Jedis jedis;

//    @Bean
    public Jedis getRedisClient() {
        if (jedis == null) {
            synchronized (temp_lock) {
                if (jedis == null) {
                    jedis = new Jedis(host, port);
                }
            }
        }
        return jedis;
    }

    public static void main(String[] args) {
        SpringApplication.run(JedisClientUtil.class, args);
    }

}