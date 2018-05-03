package com.diyishuai.redis.sortSet;

import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Describe: 请补充类描述
 */
public class Bang {

    public static void main(String[] args) {
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //创建销售线程-销售商品
        executorService.submit(new Sale());
        executorService.submit(new Sale());
        //创建报表线程-周期型计算排行榜
        executorService.submit(new BangView());
    }
}

class Sale implements Runnable {
    //店铺销售排行榜
    private static final String amountBang = "tmall:amountBang";
    //店铺订单排行榜
    private static final String orderBang = "tmall:orderBang";
    //店铺名称
    private static final String[] shops = new String[]{"小米", "华为", "魅族", "苹果", "联想", "奇酷", "中兴", "一加", "oppo"};
    //Redis客户端
    private Jedis jedis = new Jedis("127.0.0.1", 6379);
    //随机获取店铺
    private Random random = new Random();
    //随机计算价格
    private Random priceRandom = new Random();

    public void run() {
        while (true) {
            try {
                int shopIndex = random.nextInt(shops.length);
                jedis.zincrby(amountBang, priceRandom.nextInt(2500), shops[shopIndex]);
                jedis.zincrby(orderBang, 1, shops[shopIndex]);

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

}

class BangView implements Runnable {
    //店铺销售排行榜
    private static final String amountBang = "tmall:amountBang";
    //店铺订单排行榜
    private static final String orderBang = "tmall:orderBang";
    //Redis客户端
    private Jedis jedis = new Jedis("127.0.0.1", 6379);

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                System.out.println("==============店铺销售额排行==============================");
                Set<String> names = jedis.zrevrange(amountBang, 0, 4);
                for (String name : names) {
                    System.out.println(name + "         "
                            + jedis.zrevrank(amountBang, name) + "            "
                            + jedis.zscore(amountBang, name));
                }
                System.out.println("==============店铺订单量排行==============================");
                names = jedis.zrevrange(orderBang, 0, 1);
                for (String name : names) {
                    System.out.println(name + "         "
                            + jedis.zrevrank(orderBang, name) + "            "
                            + jedis.zscore(orderBang, name));
                }

                System.out.println();
                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
