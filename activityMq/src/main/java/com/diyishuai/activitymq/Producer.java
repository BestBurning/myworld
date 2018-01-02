package com.diyishuai.activitymq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Producer {
    private String user = ActiveMQConnection.DEFAULT_USER;
    private String password = ActiveMQConnection.DEFAULT_PASSWORD;
    private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private String subject = "mytopic";
    private Destination destination = null;
    private Connection connection = null;
    private Session session = null;
    private MessageProducer producer = null;

    //初始化
    private void init() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        destination = session.createTopic(subject);
        producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }

    public void produceMessage(String message) throws JMSException {
        init();
        TextMessage msg = session.createTextMessage(message);
        connection.start();
        System.out.println("Producer:-> Sending message : " + message);
        producer.send(msg);
        System.out.println("Producer:-> Message sent complete! ");
    }

    public void close() throws JMSException {
        System.out.println("Producer:->Closing connection");
        if(producer != null)
            producer.close();
        if (session != null)
            session.close();
        if (connection != null)
            connection.close();
    }

}
