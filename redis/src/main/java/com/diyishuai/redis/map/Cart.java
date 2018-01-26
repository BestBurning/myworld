package com.diyishuai.redis.map;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Describe: 购物车
 */
public class Cart {
    private Jedis jedis;

    public Cart() {
        jedis = new Jedis("127.0.0.1", 6379);
    }

    public Cart(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * 修改购物车中的商品
     *
     * @param userName  用户名
     * @param productId 商品编号
     * @param num       操作商品的数量
     */
    public void updateProduct2Cart(String userName, String productId, int num) {
        jedis.hincrBy("shop:cart:" + userName, productId, num);
    }

    /**
     * 获取用户购物车的商品信息
     *
     * @param userName
     * @return
     */
    public List<Product> getProductsByUserName(String userName) {
        List<Product> products = new ArrayList<Product>();
        Map<String, String> productMap = jedis.hgetAll("shop:cart:" + userName);
        if (productMap == null || productMap.size() == 0) {
            return products;
        }
        for (Map.Entry entry : productMap.entrySet()) {
            Product product = new Product();
            product.setId((String) entry.getKey());//获取用户购物车中商品的编号
            int num = Integer.parseInt((String) entry.getValue());//获取用户购物车中商品的数量
            product.setNum(num > 0 ? num : 0);//如果商品数量大于0，返回正常的值，如果商品小于0，初始化为0
            complementOtherField(product);//补全商品的其他信息
            products.add(product);
        }
        return products;
    }

    private void complementOtherField(Product product) {
        String productId = product.getId();
        String productJsonStr = jedis.get("shop:product:" + productId);
        Product productJson = (Product) new Gson().fromJson(productJsonStr, Product.class);
        if (productJson != null) {
            product.setName(productJson.getName());
            product.setPrice(productJson.getPrice());
        }
    }

    public static void main(String[] args) {
        //初始化商品的信息
        initData();
        //创建购物车对象
        Cart cart = new Cart();
        //创建用户
        String userName = "liudehua";
        //往用户购物车中添加商品
        cart.updateProduct2Cart(userName, "1645080454", 10);
        cart.updateProduct2Cart(userName, "1788744384", 1000);
        cart.updateProduct2Cart(userName, "1645139266", -1000);
        //打印当前用户的购物车信息
        List<Product> products = cart.getProductsByUserName(userName);
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private static void initData() {
        System.out.println("========================初始化商品信息===========================");
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //准备数据
        Product product1 = new Product("1645139266", "战地鳄2015秋冬新款马甲可脱卸帽休闲时尚无袖男士羽绒棉外套马甲", new BigDecimal("168"));
        Product product2 = new Product("1788744384", "天乐时 爸爸装加厚马甲秋冬装中年大码男士加绒马夹中老年坎肩老年人", new BigDecimal("40"));
        Product product3 = new Product("1645080454", "战地鳄2015秋冬新款马甲可脱卸帽休闲时尚无袖男士羽绒棉外套马甲", new BigDecimal("230"));
        //将数据写入到Redis
        jedis.set("shop:product:" + product1.getId(), new Gson().toJson(product1));
        jedis.set("shop:product:" + product2.getId(), new Gson().toJson(product2));
        jedis.set("shop:product:" + product3.getId(), new Gson().toJson(product3));
        //打印所有产品信息
        Set<String> allProductKeys = jedis.keys("shop:product:*"); //获取所有的商品信息
        for (String key : allProductKeys) {
            String json = jedis.get(key);
            Product product = new Gson().fromJson(json, Product.class);//从字符串中解析出对象
            System.out.println(product);
        }
        System.out.println("========================用户购物车信息如下===========================");

    }
}
