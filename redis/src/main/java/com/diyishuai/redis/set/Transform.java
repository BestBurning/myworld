package com.diyishuai.redis.set;

import redis.clients.jedis.Jedis;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Set;

/**
 * Describe: 请补充类描述
 */
public class Transform {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //浏览某商品的用户
        jedis.sadd("viewUsers", "郭靖", "黄蓉", "令狐冲", "杨过", "林冲",
                "鲁智深", "小女龙", "虚竹", "独孤求败", "张三丰", "王重阳", "张无忌"
                , "王重阳", "东方不败", "逍遥子", "乔峰", "虚竹", "段誉");

        //下单用户
        jedis.sadd("orderUsers", "郭靖", "黄蓉", "令狐冲", "杨过", "林冲",
                "鲁智深", "小女龙", "虚竹", "独孤求败", "乔峰", "虚竹", "段誉");
        //支付用户
        jedis.sadd("paymentUsers", "郭靖", "黄蓉", "令狐冲", "杨过", "独孤求败", "段誉");

        //浏览过商品的用户，有哪些下单了。
        jedis.sinterstore("view2order", "viewUsers", "orderUsers"); //求两个集合的交集


        //计算浏览某商品的用户数量 和 既浏览又下单的用户的数量
        double viewUserNum = jedis.scard("viewUsers");
        double orderUserNum = jedis.scard("view2order");
        NumberFormat formatter = new DecimalFormat("0.00");
        Double x = new Double(orderUserNum / viewUserNum);
        System.out.print("订单" + orderUserNum + "/浏览" + viewUserNum + "转化：" + formatter.format(x) + "     他们是：");
        for (String name : jedis.smembers("view2order")) {
            System.out.print(name + "  ");
        }
        System.out.println();


        //浏览并且下单的用户，最终支付的转化
        jedis.sinterstore("order2Payment", "view2order", "paymentUsers"); //求两个集合的交集
        double paymentUserNum = jedis.scard("paymentUsers");
        x = new Double(paymentUserNum / orderUserNum);
        System.out.print("支付" + paymentUserNum + "/订单" + orderUserNum + "转化：" + formatter.format(x) + "     他们是：");
        for (String name : jedis.smembers("order2Payment")) {
            System.out.print(name + "  ");
        }
        System.out.println();
        //浏览并最终支付的用户的转化
        x = new Double(paymentUserNum / viewUserNum);
        System.out.print("支付" + paymentUserNum + "/浏览" + viewUserNum + "转化：" + formatter.format(x)+"    他们是：");
        for (String name : jedis.smembers("order2Payment")) {
            System.out.print(name + "  ");
        }
        System.out.println();
    }
}
