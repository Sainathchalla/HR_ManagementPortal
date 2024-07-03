package com.FedEx.payroll_service.dto;

public class PayrollResponseDTO {
    private Long id;
    private Long employeeId;
    private double salary;
    private double deductions;
    private double bonuses;
    private String taxInformation;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getDeductions() {
        return deductions;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public double getBonuses() {
        return bonuses;
    }

    public void setBonuses(double bonuses) {
        this.bonuses = bonuses;
    }

    public String getTaxInformation() {
        return taxInformation;
    }

    public void setTaxInformation(String taxInformation) {
        this.taxInformation = taxInformation;
    }
}

