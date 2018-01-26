package com.diyishuai.storm.kafka.order;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class OrderSender {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "server01:9093,server02:9092,server03:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer(props);
        for(int i = 0; i < 100000; i++)
            producer.send(new ProducerRecord<String, String>("order", i+"", new OrderInfo().random()));

        producer.close();
    }

}
