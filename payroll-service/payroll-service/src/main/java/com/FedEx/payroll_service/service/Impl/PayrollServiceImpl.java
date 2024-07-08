package com.FedEx.payroll_service.service.Impl;

import com.FedEx.payroll_service.dto.PayrollRequestDTO;
import com.FedEx.payroll_service.dto.PayrollResponseDTO;
import com.FedEx.payroll_service.entity.Payroll;
import com.FedEx.payroll_service.repository.PayrollRepository;
import com.FedEx.payroll_service.service.PayrollService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PayrollServiceImpl implements PayrollService {

    private final PayrollRepository payrollRepository;
    private final ObjectMapper objectMapper; // Autowired ObjectMapper

    @Autowired
    public PayrollServiceImpl(PayrollRepository payrollRepository, ObjectMapper objectMapper) {
        this.payrollRepository = payrollRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public PayrollResponseDTO getPayrollDetails(Long payrollId) {
        // Retrieve Payroll entity from repository
        Payroll payroll = payrollRepository.findById(payrollId)
                .orElseThrow(() -> new RuntimeException("Payroll not found"));

        // Map Payroll entity to PayrollResponseDTO using ObjectMapper
        PayrollResponseDTO responseDTO = objectMapper.convertValue(payroll, PayrollResponseDTO.class);

        return responseDTO;
    }

    @Override
    public List<PayrollResponseDTO> getAllPayrolls() {
        List<Payroll> payrolls = payrollRepository.findAll();
        return payrolls.stream()
                .map(this::mapEntityToDto) // Using a method reference
                .collect(Collectors.toList());
    }

    private PayrollResponseDTO mapEntityToDto(Payroll payroll) {
        PayrollResponseDTO dto = new PayrollResponseDTO();
        dto.setId(payroll.getId());
        dto.setEmployeeId(payroll.getEmployeeId());
        dto.setSalary(payroll.getSalary());
        dto.setDeductions(payroll.getDeductions());
        dto.setBonuses(payroll.getBonuses());
        dto.setTaxInformation(payroll.getTaxInformation());
        // Set other fields as needed
        return dto;
    }

    @Override
    public PayrollResponseDTO createPayroll(PayrollRequestDTO payrollRequestDTO) {
        // Map PayrollRequestDTO to Payroll entity using ObjectMapper
        Payroll payroll = objectMapper.convertValue(payrollRequestDTO, Payroll.class);

        // Save the entity
        Payroll savedPayroll = payrollRepository.save(payroll);

        // Map saved Payroll entity back to PayrollResponseDTO using ObjectMapper
        PayrollResponseDTO responseDTO = objectMapper.convertValue(savedPayroll, PayrollResponseDTO.class);

        return responseDTO;
    }

    @Override
    public PayrollResponseDTO updatePayroll(Long payrollId, PayrollRequestDTO payrollRequestDTO) throws JsonProcessingException {
        // Retrieve existing Payroll entity from repository
        Payroll existingPayroll = payrollRepository.findById(payrollId)
                .orElseThrow(() -> new RuntimeException("Payroll not found"));

        // Update existing Payroll entity with data from PayrollRequestDTO using ObjectMapper
        objectMapper.readerForUpdating(existingPayroll).readValue(objectMapper.writeValueAsString(payrollRequestDTO));

        // Save the updated entity
        Payroll updatedPayroll = payrollRepository.save(existingPayroll);

        // Map updated Payroll entity to PayrollResponseDTO using ObjectMapper
        PayrollResponseDTO responseDTO = objectMapper.convertValue(updatedPayroll, PayrollResponseDTO.class);

        return responseDTO;
    }

    @Override
    public void deletePayroll(Long payrollId) {
        // Delete Payroll entity by ID
        payrollRepository.deleteById(payrollId);
    }

    @Override
    public boolean existsByEmployeeId(Long employeeId) {
        return payrollRepository.existsByEmployeeId(employeeId);
    }

    @Override
    public Long getPayrollId(Long employeeId) throws Exception {
        Payroll payroll = payrollRepository.findByEmployeeId(employeeId);
        if (payroll != null) {
            return payroll.getId();
        } else {
            throw new Exception("Payroll not found for this employeeId :: " + employeeId);
        }
    }
}
