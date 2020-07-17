package com.diyishuai.kafka.case2;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.*;

/**
 * @author Bruce
 * @date 16/9/22
 */
public class MyConsumer2 {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9093,localhost:9094");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");  //自动commit
        props.put("auto.commit.interval.ms", "1000"); //定时commit的周期
        props.put("session.timeout.ms", "30000"); //consumer活性超时时间
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        String topic = "bruce";

        KafkaConsumer<String, String> consumer = new KafkaConsumer(props);
        consumer.subscribe(Arrays.asList("bruce"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);  //poll 100 条 records
            for (ConsumerRecord<String, String> record : records)
                System.out.println("offset = "+record.offset()+", key = "+record.key()+", value = "+record.value());
        }
    }
}
