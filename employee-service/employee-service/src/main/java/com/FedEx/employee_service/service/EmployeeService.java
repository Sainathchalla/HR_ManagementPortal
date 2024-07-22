package com.FedEx.employee_service.service;

import com.FedEx.employee_service.dto.EmployeeRequestDTO;
import com.FedEx.employee_service.dto.EmployeeResponseDTO;
import com.FedEx.employee_service.entity.Employee;
import com.FedEx.employee_service.entity.NotificationMessage;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EmployeeService {
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO);
    EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employeeRequestDTO) throws JsonMappingException;
    EmployeeResponseDTO getEmployeeById(Long id);
    List<EmployeeResponseDTO> getAllEmployees();
    void deleteEmployee(Long id);

    EmployeeResponseDTO partiallyUpdateEmployee(Long id, Map<String, Object> updates) throws JsonMappingException;

//    Set<EmployeeResponseDTO> getEmployeesByIds(Set<Long> employeeIds);

    EmployeeResponseDTO authenticate(String email, String password) throws ChangeSetPersister.NotFoundException;

    boolean existsById(Long id);

    List<NotificationMessage> getNotificationsForEmployee(Long employeeId);
}
