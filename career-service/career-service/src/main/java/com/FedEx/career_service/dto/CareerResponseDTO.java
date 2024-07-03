package com.FedEx.career_service.dto;

public class CareerResponseDTO {
    private Long id;
    private String promotionDate;
    private String currentPosition;
    private String performanceReviewDate;
    private String performanceRating;
    private Long employeeId;
    private String status;

    public CareerResponseDTO() {
    }

    public CareerResponseDTO(Long id, Long employeeId, String status) {
        this.id = id;
        this.employeeId = employeeId;
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPromotionDate() {
        return promotionDate;
    }

    public void setPromotionDate(String promotionDate) {
        this.promotionDate = promotionDate;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getPerformanceReviewDate() {
        return performanceReviewDate;
    }

    public void setPerformanceReviewDate(String performanceReviewDate) {
        this.performanceReviewDate = performanceReviewDate;
    }

    public String getPerformanceRating() {
        return performanceRating;
    }

    public void setPerformanceRating(String performanceRating) {
        this.performanceRating = performanceRating;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
