package com.diyishuai.redis.string;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Describe: 请补充类描述
 */
public class StringMain {

    public static void main(String[] args) throws InterruptedException {
        //创建Jedis客户端
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        //操作一个String字符串
        jedis.set("name", "liudehua"); //插入一个名字，叫做刘德华
        System.out.println(jedis.get("name")); //读取一个名字

        //对string类型数据进行增减,前提是kv对应的值是数字
        jedis.set("age", "17");//给用户刘德华设置年龄，17岁
        jedis.incr("age");//让用户刘德华年龄增加一岁
        System.out.println(jedis.get("age")); //打印结果 18
        jedis.decr("age");//让刘德华年轻一岁
        System.out.println(jedis.get("age"));//在18的基础上，减一岁，变回17

        //一次性插入多条数据 。为江湖大侠设置绝杀技能
        jedis.mset("AAA", "Mysql数据库的操作"
                , "BBB", "熟悉LINXU操作系统"
                , "CCC", "熟悉SSH、SSM框架及配置"
                , "DDD", "熟悉Spring框架，mybatis框架，Spring IOC MVC的整合，Spring和Mybatis的整合");
        List<String> results = jedis.mget("AAA", "BBB", "CCC", "DDD");
        for (String value : results) {
            System.out.println(value);
    }

        //设置字段的自动过期
        jedis.setex("wumai", 10, "我们活在仙境中"); //让仙境保持10秒钟
        while (jedis.exists("wumai")) {
            System.out.println("真是天上人间呀！");
            Thread.sleep(1000);
        }



        System.out.println();
        //对已经存在的字段设置过期时间
        jedis.set("wumai", "我们活在仙境中");
        jedis.expire("wumai", 10); //让天上人间的感觉保持更长的时间
        while (jedis.exists("wumai")) {
            System.out.println("真是天上人间呀！");
            Thread.sleep(1000);
        }

    }
}
