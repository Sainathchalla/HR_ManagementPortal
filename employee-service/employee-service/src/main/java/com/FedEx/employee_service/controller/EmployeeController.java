package com.FedEx.employee_service.controller;

import com.FedEx.employee_service.dto.EmployeeRequestDTO;
import com.FedEx.employee_service.dto.EmployeeResponseDTO;
import com.FedEx.employee_service.service.EmployeeService;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        EmployeeResponseDTO employee = employeeService.createEmployee(employeeRequestDTO);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequestDTO employeeRequestDTO) throws JsonMappingException {
        EmployeeResponseDTO employee = employeeService.updateEmployee(id, employeeRequestDTO);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeResponseDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> partiallyUpdateEmployee(@PathVariable Long id, @RequestBody Map<String, Object> updates) throws JsonMappingException {
        EmployeeResponseDTO employee = employeeService.partiallyUpdateEmployee(id, updates);
        return ResponseEntity.ok(employee);
    }
}
