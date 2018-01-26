package com.diyishuai.storm.kafka;

import com.diyishuai.storm.kafka.order.OrderInfo;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.xml.bind.JAXBIntrospector.getValue;

public class OrderBolt extends BaseRichBolt{

    private JedisPool pool;

    private Map<String,String> infoMap;

    public static final String REDIS_HOST = "server03";
    public static final String REDIS_PASSWORD = "admin";

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        //change "maxActive" -> "maxTotal" and "maxWait" -> "maxWaitMillis" in all examples
        JedisPoolConfig config = new JedisPoolConfig();
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(5);
        //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
        //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        config.setMaxTotal(1000 * 100);
        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        config.setMaxWaitMillis(30);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        /**
         *如果你遇到 java.net.SocketTimeoutException: Read timed out exception的异常信息
         *请尝试在构造JedisPool的时候设置自己的超时值. JedisPool默认的超时时间是2秒(单位毫秒)
         */
        pool = new JedisPool(config, REDIS_HOST, 6379,2000,REDIS_PASSWORD);


        infoMap =  new HashMap<>();
        //        key:value
        //index:productID:info---->Map
        //  productId-----<各个业务线，各个品类，各个店铺，各个品牌，每个商品>
        infoMap.put("b","3c");
        infoMap.put("c","phone");
        infoMap.put("s","121");
        infoMap.put("p","iphone");
    }

    @Override
    public void execute(Tuple tuple) {
        //获取kafkaSpout发送过来的数据，是一个json
        List<Object> values = tuple.getValues();
        String s = String.valueOf(values.get(values.size()-1));
        //解析json

        OrderInfo orderInfo = null;

        try {
            orderInfo = (OrderInfo) new Gson().fromJson(s, OrderInfo.class);
            //整个网站，各个业务线，各个品类，各个店铺，各个品牌，每个商品
            //获取整个网站的金额统计指标
            Jedis jedis = pool.getResource();
            jedis.incrBy("totalAmount",orderInfo.getProductPrice());
            //获取商品所属业务线的指标信息
            String bid =  getBubyProductId(orderInfo.getProductId(),"b");
            jedis.incrBy(bid+"Amount",orderInfo.getProductPrice());
            jedis.close();
        } catch (JsonSyntaxException e) {
            System.out.println("json异常");
        }

    }

    private String getBubyProductId(String productId, String type) {
        return infoMap.get(type);

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
