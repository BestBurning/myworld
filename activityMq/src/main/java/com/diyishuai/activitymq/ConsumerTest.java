package com.diyishuai.activitymq;

import javax.jms.JMSException;

public class ConsumerTest implements Runnable{

    private static Thread t = null;

    public static void main(String[] args) {
        t = new Thread(new ConsumerTest());
        t.setDaemon(false);
        t.start();
    }

    @Override
    public void run() {
        Consumer consumer = new Consumer();
        try {
            consumer.consumerMessage();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        while (consumer.isconnection){
        }
    }
}
