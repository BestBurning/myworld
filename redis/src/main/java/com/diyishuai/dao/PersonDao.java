package com.diyishuai.dao;

import com.diyishuai.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

/**
 * Created by Bruce on 16/7/21.
 */
public class PersonDao {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Resource(name = "stringRedisTemplate")
    ValueOperations<String,String> valueOpsStr;

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Resource(name = "redisTemplate")
    ValueOperations<Object,Object> valueOps;


    public void stringRedisTemplateDemo(){
        valueOpsStr.set("xx","yy");
    }

    public void save(Person person){
        valueOps.set(person.getId(),person);
    }

    public String getString(){
        return valueOpsStr.get("xx");
    }

    public Person getPerson(){
        return (Person) valueOps.get("1");
    }


}
