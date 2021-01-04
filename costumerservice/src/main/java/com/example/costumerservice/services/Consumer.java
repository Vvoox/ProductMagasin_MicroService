package com.example.costumerservice.services;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

@Log4j2
public class Consumer {

        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://localhost:61616");

    {
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic("topic1");
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {
                @SneakyThrows
                @Override
                public void onMessage(Message message) {
                    if(message instanceof TextMessage){
                        TextMessage textMessage = (TextMessage) message;
                        log.info(textMessage.getText());
                    }
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


}
