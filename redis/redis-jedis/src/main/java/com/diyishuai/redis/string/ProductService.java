package com.diyishuai.redis.string;

import com.google.gson.Gson;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

/**
 * Describe: 保存Product对象到redis中
 */
public class ProductService {

    @Test
    public void saveProduct2Redis() throws Exception {
        //初始化刘德华的基本信息
        Person person = new Person("刘德华", 17);
        //将刘德华的信息保存到Redis中
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //直接保存对象的toString方法，这种方法不反序列化对象
        jedis.set("user:liudehua:str", person.toString());
        System.out.println(jedis.get("user:liudehua:str"));

        //保存序列化之后的对象
        jedis.set("user:liudehua:obj".getBytes(), getBytesByProduct(person));
        byte[] productBytes = jedis.get("user:liudehua:obj".getBytes());
        Person pByte = getProductByBytes(productBytes);
        System.out.println(pByte.getName()+"  " +pByte.getAge());

        //保存Json化之后的对象
        jedis.set("user:liudehua:json", new Gson().toJson(person));
        String personJson = jedis.get("user:liudehua:json");
        Person pjson = new Gson().fromJson(personJson, Person.class);
        System.out.println(pjson.getName()+"  "+ pjson.getAge());


    }

    /**
     * 从字节数组中读取Java对象
     *
     * @param productBytes
     * @return
     * @throws Exception
     */
    public Person getProductByBytes(byte[] productBytes) throws Exception {
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(productBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
        return (Person) objectInputStream.readObject();
    }

    /**
     * 将对象转化成Byte数组
     *
     * @param product
     * @return
     * @throws Exception
     */
    public byte[] getBytesByProduct(Person product) throws Exception {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(ba);
        oos.writeObject(product);
        oos.flush();
        return ba.toByteArray();
    }
}
