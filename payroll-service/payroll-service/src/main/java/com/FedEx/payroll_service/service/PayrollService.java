package com.FedEx.payroll_service.service;


import com.FedEx.payroll_service.dto.PayrollRequestDTO;
import com.FedEx.payroll_service.dto.PayrollResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;


public interface PayrollService {
    PayrollResponseDTO getPayrollDetails(Long payrollId);

    PayrollResponseDTO createPayroll(PayrollRequestDTO payrollRequestDTO);

    PayrollResponseDTO updatePayroll(Long payrollId, PayrollRequestDTO payrollRequestDTO) throws JsonProcessingException;

    void deletePayroll(Long payrollId);

    List<PayrollResponseDTO> getAllPayrolls();

    boolean existsByEmployeeId(Long employeeId);

    Long getPayrollId(Long employeeId) throws Exception;
}

