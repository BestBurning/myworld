package com.diyishuai.kafka.case1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author Bruce
 * @date 16/9/20
 */
public class MyProducer1 {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer(props);
        for(int i = 0; i < 100; i++){
            producer.send(new ProducerRecord<String, String>("request", Integer.toString(i), "{\"@timestamp\":\"2018-05-08T10:24:42.083Z\",\"beat\":{\"hostname\":\"prod-adx\",\"name\":\"prod-adx\",\"version\":\"5.5.3\"},\"input_type\":\"log\",\"message\":\"020180508182441eae0c746c8fe433cbe5051ad8e51323a\\u00011\\u00015ad6d994ed06ec36f8e91d75\\u0001com.centrixlink.test.android\\u0001\\u0001\\u00011.0.0\\u00016.0.1\\u0001Android\\u00010\\u0001CPH1607\\u0001OPPO\\u00011525775081493\\u00011\\u00010\\u00011080\\u00011920\\u00010\\u0001331378d1bb5904828f67eb083415a405\\u0001\\u0001\\u0001c48fc43a89e6d15d\\u0001\\u00014C:1A:3D:74:5E:B6\\u0001315a2161-34dc-4f65-8a9e-e79025fda183\\u00012\\u0001\\u0001localhost\\u0001172.17.0.111\\u0001Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; en-US; rv:1.9.2.16) Gecko/20110319 Firefox/3.6.16\\u0001\\u0001\\u0001\\u00011080\\u00011920\\u0001\\u0001\\u00012\\u0001\\u00010.0\\u0001USD\\u0001\\u00010.0\\u0001USD\\u0001\\u00010.0\",\"offset\":9809,\"source\":\"/mnt/logs/data/ssp/request.2018-05-08.log\",\"type\":\"log\"}"));
            System.out.println(i);
        }
        producer.close();
    }

}
