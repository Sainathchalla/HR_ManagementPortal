package com.FedEx.payroll_service.repository;

import com.FedEx.payroll_service.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    // Additional query methods can be added if needed
}

