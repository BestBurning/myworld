package com.diyishuai.activitymq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 *
 */
public class Consumer implements MessageListener,ExceptionListener{

    public boolean isconnection = false;
    private String user = ActiveMQConnection.DEFAULT_USER;
    private String password = ActiveMQConnection.DEFAULT_PASSWORD;
    private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private String subject = "mytopic";
    private Destination destination = null;
    private Connection connection = null;
    private Session session = null;
    private MessageConsumer consumer = null;

    //初始化
    private void init() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        destination = session.createTopic(subject);
        consumer = session.createConsumer(destination);
    }



    public void consumerMessage() throws JMSException {
        init();
        connection.start();
        consumer.setMessageListener(this);
        connection.setExceptionListener(this);
        isconnection = true;
        System.out.println("Consumer:-> Begin listening...");
    }

    public void close() throws JMSException {
        System.out.println("Consumer:->Closing connection");
        if(consumer != null)
            consumer.close();
        if (session != null)
            session.close();
        if (connection != null)
            connection.close();
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage){
                TextMessage txtMsg = (TextMessage) message;
                String msg = txtMsg.getText();
                System.out.println("Consumer:-> Received:"+msg);
            }else {
                System.out.println("Consumer:-> Received:"+message);
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onException(JMSException e) {
        isconnection = false;
    }
}
