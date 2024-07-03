package com.FedEx.employee_service.service;

import com.FedEx.employee_service.dto.EmployeeRequestDTO;
import com.FedEx.employee_service.dto.EmployeeResponseDTO;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO);
    EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employeeRequestDTO) throws JsonMappingException;
    EmployeeResponseDTO getEmployeeById(Long id);
    List<EmployeeResponseDTO> getAllEmployees();
    void deleteEmployee(Long id);

    EmployeeResponseDTO partiallyUpdateEmployee(Long id, Map<String, Object> updates) throws JsonMappingException;
}
