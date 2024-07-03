package com.FedEx.career_service.entity;

import jakarta.persistence.*;

@Entity
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "promotion_date")
    private String promotionDate;

    @Column(name = "current_position")
    private String currentPosition;

    @Column(name = "performance_review_date")
    private String performanceReviewDate;

    @Column(name = "performance_rating")
    private String performanceRating;

    @Column(name = "status")
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
