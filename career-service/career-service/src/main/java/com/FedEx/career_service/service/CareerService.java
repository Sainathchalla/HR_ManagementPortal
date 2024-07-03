package com.FedEx.career_service.service;

import com.FedEx.career_service.dto.CareerRequestDTO;
import com.FedEx.career_service.dto.CareerResponseDTO;

import java.util.List;

public interface CareerService {

    CareerResponseDTO createCareer(CareerRequestDTO careerRequestDTO);

    List<CareerResponseDTO> getAllCareers();

    CareerResponseDTO getCareerById(Long id);

    CareerResponseDTO updateCareer(Long id, CareerRequestDTO careerRequestDTO);

    CareerResponseDTO patchCareer(Long id, CareerRequestDTO careerRequestDTO);

    void deleteCareer(Long id);

    CareerResponseDTO submitPromotionRequest(CareerRequestDTO careerRequestDTO);

    List<CareerResponseDTO> getAllPromotionRequests();

    CareerResponseDTO processPromotionRequest(Long requestId, CareerRequestDTO careerRequestDTO);

    CareerResponseDTO createPerformanceReview(CareerRequestDTO careerRequestDTO);

    List<CareerResponseDTO> getAllPerformanceReviews();

    List<CareerResponseDTO> getPerformanceReviewsByEmployeeId(Long employeeId);
}
