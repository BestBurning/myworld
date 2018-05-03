package com.diyishuai.redis.string;

import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * Describe: 擂台
 */
public class Arena implements Runnable {

    private Random random = new Random();
    private String redisKey;
    private Jedis jedis;
    private String arenaName;

    public Arena(String redisKey, String arenaName) {
        this.redisKey = redisKey;
        this.arenaName = arenaName;
    }

    public void run() {
        jedis = new Jedis("127.0.0.1",6379);
        String[] daxias = new String[]{"郭靖", "黄蓉", "令狐冲", "杨过", "林冲",
                "鲁智深", "小女龙", "虚竹", "独孤求败", "张三丰", "王重阳", "张无忌"
                , "王重阳", "东方不败", "逍遥子", "乔峰", "虚竹", "段誉"
                , "韦小宝", "王语嫣", "周芷若", "峨眉师太", "慕容复"};
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int p1 = random.nextInt(daxias.length);
            int p2 = random.nextInt(daxias.length);
            while (p1 == p2) { //如果两个大侠出场名字一样，换一个人
                p2 = random.nextInt(daxias.length);
            }
            System.out.println("在擂台" + arenaName + "上   大侠" + daxias[p1] + " VS " + daxias[p2]);
            jedis.incr(redisKey);
        }
    }
}
