package com.FedEx.employee_service.service.Impl;

import com.FedEx.employee_service.dto.EmployeeRequestDTO;
import com.FedEx.employee_service.dto.EmployeeResponseDTO;
import com.FedEx.employee_service.entity.Employee;
import com.FedEx.employee_service.repository.EmployeeRepository;
import com.FedEx.employee_service.service.EmployeeService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = objectMapper.convertValue(employeeRequestDTO, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        return objectMapper.convertValue(savedEmployee, EmployeeResponseDTO.class);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employeeRequestDTO) throws JsonMappingException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        objectMapper.updateValue(employee, employeeRequestDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return objectMapper.convertValue(savedEmployee, EmployeeResponseDTO.class);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        return objectMapper.convertValue(employee, EmployeeResponseDTO.class);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employee -> objectMapper.convertValue(employee, EmployeeResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeResponseDTO partiallyUpdateEmployee(Long id, Map<String, Object> updates) throws JsonMappingException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        objectMapper.updateValue(employee, updates);

        Employee savedEmployee = employeeRepository.save(employee);
        return objectMapper.convertValue(savedEmployee, EmployeeResponseDTO.class);
    }

//    @Override
//    public Set<EmployeeResponseDTO> getEmployeesByIds(Set<Long> employeeIds) {
//        return employeeRepository.findByIdIn(employeeIds);
//    }

    @Override
    public EmployeeResponseDTO authenticate(String email, String password) {
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(email);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            if (employee.getPassword().equals(password)) {
                EmployeeResponseDTO dto = objectMapper.convertValue(employee, EmployeeResponseDTO.class);
                return dto;
            }
        }
        throw new RuntimeException("Employee not found");
    }

    @Override
    public boolean existsById(Long id) {
        return employeeRepository.existsById(id);
    }

}