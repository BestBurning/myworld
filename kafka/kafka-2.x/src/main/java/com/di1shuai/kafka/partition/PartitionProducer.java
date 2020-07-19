package com.di1shuai.kafka.partition;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * @author: Shea
 * @date: 2020/7/19
 * @description:
 */
public class PartitionProducer {
    static Properties props = new Properties();
    static Producer<String, String> producer;

    static String topic = "test";

    static {

        props.put("bootstrap.servers", "kafka:9092,kafka:9093,kafka:9094");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //指定分区器
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, ZeroPartitioner.class.getName());
        producer = new KafkaProducer(props);
    }

    public static void main(String[] args) {


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

        producer.close();

    }


}
