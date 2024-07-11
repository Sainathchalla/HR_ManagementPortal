package com.FedEx.employee_service.Jms;

import com.FedEx.employee_service.dto.EmployeeRequestDTO;
import com.FedEx.employee_service.dto.EmployeeResponseDTO;
import com.FedEx.employee_service.entity.Employee;
import com.FedEx.employee_service.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class EmployeeMessageListener {
//
//    @Autowired
//    private JmsTemplate jmsTemplate;
//
//    @Autowired
//    private EmployeeService employeeService;
//
//    @Value("${jms.queue.destination}")
//    private String destinationQueue;
//
//    @JmsListener(destination = "${jms.queue.destination}")
//    public void receiveMessage(Long employeeId) {
//        EmployeeResponseDTO employee = employeeService.getEmployeeById(employeeId);
//        // Perform necessary actions with the received employee data
//    }
//}
