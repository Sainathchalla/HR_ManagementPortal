package com.FedEx.payroll_service.controller;

import com.FedEx.payroll_service.dto.PayrollRequestDTO;
import com.FedEx.payroll_service.dto.PayrollResponseDTO;
import com.FedEx.payroll_service.service.PayrollService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payroll")
public class PayrollController {

    private final PayrollService payrollService;

    @Autowired
    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @GetMapping("/{payrollId}")
    public ResponseEntity<PayrollResponseDTO> getPayrollDetails(@PathVariable Long payrollId) {
        PayrollResponseDTO payroll = payrollService.getPayrollDetails(payrollId);
        return ResponseEntity.ok().body(payroll);
    }

    @GetMapping
    public ResponseEntity<List<PayrollResponseDTO>> getAllPayrolls() {
        List<PayrollResponseDTO> payrolls = payrollService.getAllPayrolls();
        return ResponseEntity.ok().body(payrolls);
    }

    @PostMapping
    public ResponseEntity<PayrollResponseDTO> createPayroll(@RequestBody PayrollRequestDTO payrollRequestDTO) {
        PayrollResponseDTO createdPayroll = payrollService.createPayroll(payrollRequestDTO);
        return new ResponseEntity<>(createdPayroll, HttpStatus.CREATED);
    }

    @PutMapping("/{payrollId}")
    public ResponseEntity<PayrollResponseDTO> updatePayroll(@PathVariable Long payrollId,
                                                            @RequestBody PayrollRequestDTO payrollRequestDTO) throws JsonProcessingException {
        PayrollResponseDTO updatedPayroll = payrollService.updatePayroll(payrollId, payrollRequestDTO);
        return ResponseEntity.ok().body(updatedPayroll);
    }

    @DeleteMapping("/{payrollId}")
    public ResponseEntity<Void> deletePayroll(@PathVariable Long payrollId) {
        payrollService.deletePayroll(payrollId);
        return ResponseEntity.noContent().build();
    }
}

