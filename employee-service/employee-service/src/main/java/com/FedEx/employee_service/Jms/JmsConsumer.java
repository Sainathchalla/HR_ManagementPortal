package com.FedEx.employee_service.Jms;

import com.FedEx.employee_service.entity.NotificationMessage;
import com.FedEx.employee_service.repository.NotificationMessageRepository;
import jakarta.jms.JMSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class JmsConsumer {
    @Autowired
    NotificationMessageRepository notificationMessageRepository;

    @JmsListener(destination = "notificationQueue")
    public void receiveMessage(NotificationMessage message) throws JMSException {

        System.out.println("Received message: " + message);
        NotificationMessage obj = new NotificationMessage();
        obj.setProjectId(message.getProjectId());
        obj.setProjectName(message.getProjectName());
        obj.setStatus(message.getStatus());
        obj.setEmployeeIds(message.getEmployeeIds());

        notificationMessageRepository.save(obj);
    }
}
