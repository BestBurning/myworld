package com.diyishuai.activitymq;

import javax.jms.JMSException;
import java.util.Random;

public class ProducerTest {

    public static void main(String[] args) throws InterruptedException, JMSException {
        Producer producer = new Producer();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Thread.sleep(random.nextInt(10)*100);
            producer.produceMessage("Hello,world-- "+ i);
            producer.close();
        }
    }

}
