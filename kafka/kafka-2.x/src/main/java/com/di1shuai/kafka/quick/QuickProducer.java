package com.di1shuai.kafka.quick;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * @author: Shea
 * @date: 2020/7/17
 * @description:
 */
public class QuickProducer {

    static Properties props = new Properties();
    static Producer<String, String> producer;

    static String topic = "test";

    static {

        props.put("bootstrap.servers", "kafka:9092,kafka:9093,kafka:9094");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer(props);
    }

    public static void main(String[] args) {
        try {
//            defaultSend();
//            callbackSend();
            keyNullSend();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }

    private static void keyNullSend() {
        System.out.println("========= Key Null Send ==========");
        for (int i = 0; i < 100; i++) {
            ProducerRecord<String, String> message = new ProducerRecord<>(topic, null, Integer.toString(i));
            producer.send(message, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println("offset : \t " + metadata.offset() + "\t partition : \t " + metadata.partition());
                    } else {
                        exception.printStackTrace();
                    }
                }
            });
        }
    }

    static void callbackSend() {
        System.out.println("========= Callback Send ==========");
        for (int i = 0; i < 50; i++) {
            ProducerRecord<String, String> message = new ProducerRecord<>(topic, Integer.toString(i), Integer.toString(i));
            producer.send(message, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println("offset : \t " + metadata.offset() + "\t partition : \t " + metadata.partition());
                    } else {
                        exception.printStackTrace();
                    }
                }
            });
        }
    }

    static void defaultSend() {
        System.out.println("========= Default Send ==========");
        for (int i = 0; i < 50; i++) {
            producer.send(new ProducerRecord<String, String>(topic, Integer.toString(i), Integer.toString(i)));
        }
    }


}
