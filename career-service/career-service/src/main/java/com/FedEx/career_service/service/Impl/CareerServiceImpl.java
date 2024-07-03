package com.FedEx.career_service.service.Impl;

import com.FedEx.career_service.dto.CareerRequestDTO;
import com.FedEx.career_service.dto.CareerResponseDTO;
import com.FedEx.career_service.entity.Career;
import com.FedEx.career_service.repository.CareerRepository;
import com.FedEx.career_service.service.CareerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CareerServiceImpl implements CareerService {

    private Map<Long, CareerResponseDTO> promotionRequests = new HashMap<>();
    private Map<Long, CareerResponseDTO> performanceReviews = new HashMap<>();
    private Long nextRequestId = 1L;

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public CareerResponseDTO createCareer(CareerRequestDTO careerRequestDTO) {
        Career career = objectMapper.convertValue(careerRequestDTO, Career.class);
        Career savedCareer = careerRepository.save(career);
        return objectMapper.convertValue(savedCareer, CareerResponseDTO.class);
    }

    @Override
    public List<CareerResponseDTO> getAllCareers() {
        List<Career> careers = careerRepository.findAll();
        return careers.stream()
                .map(career -> objectMapper.convertValue(career, CareerResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CareerResponseDTO getCareerById(Long id) {
        Career career = careerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Career not found"));
        return objectMapper.convertValue(career, CareerResponseDTO.class);
    }

    @Override
    public CareerResponseDTO updateCareer(Long id, CareerRequestDTO careerRequestDTO) {
        Career career = careerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Career not found"));
        career.setPromotionDate(careerRequestDTO.getPromotionDate());
        career.setCurrentPosition(careerRequestDTO.getCurrentPosition());
        career.setPerformanceReviewDate(careerRequestDTO.getPerformanceReviewDate());
        career.setPerformanceRating(careerRequestDTO.getPerformanceRating());
        career.setStatus(careerRequestDTO.getStatus());

        Career savedCareer = careerRepository.save(career);
        return objectMapper.convertValue(savedCareer, CareerResponseDTO.class);
    }

    @Override
    public CareerResponseDTO patchCareer(Long id, CareerRequestDTO careerRequestDTO) {
        Career career = careerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Career not found"));

        if (careerRequestDTO.getPromotionDate() != null) {
            career.setPromotionDate(careerRequestDTO.getPromotionDate());
        }
        if (careerRequestDTO.getCurrentPosition() != null) {
            career.setCurrentPosition(careerRequestDTO.getCurrentPosition());
        }
        if (careerRequestDTO.getPerformanceReviewDate() != null) {
            career.setPerformanceReviewDate(careerRequestDTO.getPerformanceReviewDate());
        }
        if (careerRequestDTO.getPerformanceRating() != null) {
            career.setPerformanceRating(careerRequestDTO.getPerformanceRating());
        }
        if (careerRequestDTO.getEmployeeId() != null) {
            career.setEmployeeId(careerRequestDTO.getEmployeeId());
        }
        if (careerRequestDTO.getStatus() != null) {
            career.setStatus(careerRequestDTO.getStatus());
        }

        Career savedCareer = careerRepository.save(career);
        return objectMapper.convertValue(savedCareer, CareerResponseDTO.class);
    }

    @Override
    public void deleteCareer(Long id) {
        Career career = careerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Career not found"));
        careerRepository.delete(career);
    }


    @Override
    public CareerResponseDTO submitPromotionRequest(CareerRequestDTO careerRequestDTO) {
        CareerResponseDTO careerResponseDTO = new CareerResponseDTO(nextRequestId, careerRequestDTO.getEmployeeId(), "Pending");
        promotionRequests.put(nextRequestId, careerResponseDTO);
        nextRequestId++;
        return careerResponseDTO;
    }

    @Override
    public List<CareerResponseDTO> getAllPromotionRequests() {
        return new ArrayList<>(promotionRequests.values());
    }

    @Override
    public CareerResponseDTO processPromotionRequest(Long requestId, CareerRequestDTO careerRequestDTO) {
        CareerResponseDTO careerResponseDTO = promotionRequests.get(requestId);
        if (careerResponseDTO != null) {
            careerResponseDTO.setStatus(careerRequestDTO.getStatus());
            promotionRequests.put(requestId, careerResponseDTO);
        }
        return careerResponseDTO;
    }

    @Override
    public CareerResponseDTO createPerformanceReview(CareerRequestDTO careerRequestDTO) {
        CareerResponseDTO careerResponseDTO = new CareerResponseDTO(nextRequestId, careerRequestDTO.getEmployeeId(), "Created");
        performanceReviews.put(nextRequestId, careerResponseDTO);
        nextRequestId++;
        return careerResponseDTO;
    }

    @Override
    public List<CareerResponseDTO> getAllPerformanceReviews() {
        return new ArrayList<>(performanceReviews.values());
    }

    @Override
    public List<CareerResponseDTO> getPerformanceReviewsByEmployeeId(Long employeeId) {
        List<CareerResponseDTO> reviews = new ArrayList<>();
        for (CareerResponseDTO review : performanceReviews.values()) {
            if (review.getEmployeeId().equals(employeeId)) {
                reviews.add(review);
            }
        }
        return reviews;
    }
}
