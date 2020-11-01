package com.di1shuai.kafka.transaction;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author: Shea
 * @date: 2020/7/25
 * @description:
 */
public class TransactionProducer {

    static Properties props = new Properties();
    static Producer<String, String> producer;

    {
        props.put("bootstrap.servers", "kafka:9092,kafka:9093,kafka:9094");
        props.put("transactional.id", "my-transactional-id");
        producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());

    }

    static void sendSuccess() {
        producer.initTransactions();
        try {
            producer.beginTransaction();
            for (int i = 0; i < 100; i++) {
                producer.send(new ProducerRecord<>("test", Integer.toString(i), Integer.toString(i)));
            }
            producer.commitTransaction();
        } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
            // We can't recover from these exceptions, so our only option is to close the producer and exit.
            producer.close();
        } catch (KafkaException e) {
            // For all other exceptions, just abort the transaction and try again.
            producer.abortTransaction();
        }
    }

    static void sendFail() {
        producer.initTransactions();
        try {
            producer.beginTransaction();
            for (int i = 0; i < 100; i++) {
                producer.send(new ProducerRecord<>("test", Integer.toString(i), Integer.toString(i)));
            }
            int i = 1 / 0;
            producer.commitTransaction();
        } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
            // We can't recover from these exceptions, so our only option is to close the producer and exit.
            producer.close();
        } catch (Exception e) {
            // For all other exceptions, just abort the transaction and try again.
            System.err.println("发生异常，进行回滚");
            producer.abortTransaction();
        }
    }


    public static void main(String[] args) {
        sendSuccess();
        sendFail();
        producer.close();
    }


}
