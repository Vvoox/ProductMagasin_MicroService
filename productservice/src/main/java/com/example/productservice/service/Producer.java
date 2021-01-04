package com.example.productservice.service;

import lombok.extern.log4j.Log4j2;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Log4j2
@Service
public class Producer {

    public static void sendMessage(String msg){
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        {
            try{
                Connection connection = connectionFactory.createConnection();
                connection.start();
                Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
                Destination destination = session.createTopic("topic1");
                MessageProducer producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                TextMessage message = session.createTextMessage();
                message.setText(msg);
                producer.send(message);
                session.close();
                connection.close();
            }catch(JMSException e){
                e.printStackTrace();
            }
        }
    }



}
