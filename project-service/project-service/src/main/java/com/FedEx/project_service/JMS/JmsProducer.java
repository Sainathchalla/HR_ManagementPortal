package com.FedEx.project_service.JMS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsProducer {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public <T> void sendMessage(String destination, T message) {
        jmsTemplate.convertAndSend(destination, message);
    }
}
