package com.FedEx.employee_service.controller;

import com.FedEx.employee_service.entity.NotificationMessage;
import com.FedEx.employee_service.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{employeeId}")
    public List<NotificationMessage> getNotifications(@PathVariable Long employeeId) {
        return employeeService.getNotificationsForEmployee(employeeId);
    }
}
