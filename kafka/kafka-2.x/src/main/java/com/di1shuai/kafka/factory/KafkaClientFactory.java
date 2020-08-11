package com.di1shuai.kafka.factory;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

/**
 * @author: Shea
 * @date: 2020/7/25
 * @description:
 */
public class KafkaClientFactory {

    public static KafkaProducer getProducer(Properties configProperties) {
        return new KafkaProducer(configProperties);
    }

    public static KafkaConsumer getConsumer(Properties configProperties) {
        return new KafkaConsumer(configProperties);
    }


}
