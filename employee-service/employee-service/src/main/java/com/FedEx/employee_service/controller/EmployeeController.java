package com.FedEx.employee_service.controller;

import com.FedEx.employee_service.dto.EmployeeRequestDTO;
import com.FedEx.employee_service.dto.EmployeeResponseDTO;
import com.FedEx.employee_service.entity.Employee;
import com.FedEx.employee_service.service.EmployeeService;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        EmployeeResponseDTO employee = employeeService.createEmployee(employeeRequestDTO);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> employeeExists(@PathVariable Long id) {
        boolean exists = employeeService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequestDTO employeeRequestDTO) throws JsonMappingException {
        EmployeeResponseDTO employee = employeeService.updateEmployee(id, employeeRequestDTO);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<EmployeeResponseDTO> authenticate(
            @RequestHeader("emailId") String emailId,
            @RequestHeader("password") String password) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(employeeService.authenticate(emailId, password));
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

//    @PostMapping("/get-by-ids")
//    public ResponseEntity<List<EmployeeResponseDTO>> getEmployeesByIds(@RequestBody Set<Long> employeeIds) {
//        Set<EmployeeResponseDTO> employees = employeeService.getEmployeesByIds(employeeIds);
//        List<EmployeeResponseDTO> employeeList = new ArrayList<>(employees);
//        return ResponseEntity.ok(employeeList); // Convert Set to List for ResponseEntity
//    }
}
