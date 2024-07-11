package com.FedEx.career_service.controller;

import com.FedEx.career_service.dto.CareerRequestDTO;
import com.FedEx.career_service.dto.CareerResponseDTO;
import com.FedEx.career_service.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/careers")
@CrossOrigin(origins = "http://localhost:4200")
public class CareerController {

    @Autowired
    private CareerService careerService;

    @PostMapping
    public ResponseEntity<CareerResponseDTO> createCareer(@RequestBody CareerRequestDTO careerRequestDTO) {
        CareerResponseDTO createdCareer = careerService.createCareer(careerRequestDTO);
        return ResponseEntity.ok(createdCareer);
    }

    @GetMapping
    public ResponseEntity<List<CareerResponseDTO>> getAllCareers() {
        List<CareerResponseDTO> careers = careerService.getAllCareers();
        return ResponseEntity.ok(careers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CareerResponseDTO> getCareerById(@PathVariable Long id) {
        CareerResponseDTO career = careerService.getCareerById(id);
        return ResponseEntity.ok(career);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CareerResponseDTO> updateCareer(@PathVariable Long id, @RequestBody CareerRequestDTO careerRequestDTO) {
        CareerResponseDTO updatedCareer = careerService.updateCareer(id, careerRequestDTO);
        return ResponseEntity.ok(updatedCareer);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CareerResponseDTO> patchCareer(@PathVariable Long id, @RequestBody CareerRequestDTO careerRequestDTO) {
        CareerResponseDTO patchedCareer = careerService.patchCareer(id, careerRequestDTO);
        return ResponseEntity.ok(patchedCareer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCareer(@PathVariable Long id) {
        careerService.deleteCareer(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/promotion-requests")
    public ResponseEntity<CareerResponseDTO> submitPromotionRequest(@RequestBody CareerRequestDTO careerRequestDTO) {
        CareerResponseDTO submittedRequest = careerService.submitPromotionRequest(careerRequestDTO);
        return ResponseEntity.ok(submittedRequest);
    }

    @GetMapping("/promotion-requests")
    public ResponseEntity<List<CareerResponseDTO>> getAllPromotionRequests() {
        List<CareerResponseDTO> promotionRequests = careerService.getAllPromotionRequests();
        return ResponseEntity.ok(promotionRequests);
    }

    @PutMapping("/promotion-requests/{requestId}")
    public ResponseEntity<CareerResponseDTO> processPromotionRequest(@PathVariable Long requestId, @RequestBody CareerRequestDTO careerRequestDTO) {
        CareerResponseDTO processedRequest = careerService.processPromotionRequest(requestId, careerRequestDTO);
        return ResponseEntity.ok(processedRequest);
    }

    @PostMapping("/performance-reviews")
    public ResponseEntity<CareerResponseDTO> createPerformanceReview(@RequestBody CareerRequestDTO careerRequestDTO) {
        CareerResponseDTO createdReview = careerService.createPerformanceReview(careerRequestDTO);
        return ResponseEntity.ok(createdReview);
    }

    @GetMapping("/performance-reviews")
    public ResponseEntity<List<CareerResponseDTO>> getAllPerformanceReviews() {
        List<CareerResponseDTO> performanceReviews = careerService.getAllPerformanceReviews();
        return ResponseEntity.ok(performanceReviews);
    }

    @GetMapping("/performance-reviews/{employeeId}")
    public ResponseEntity<List<CareerResponseDTO>> getPerformanceReviewsByEmployeeId(@PathVariable Long employeeId) {
        List<CareerResponseDTO> reviewsByEmployee = careerService.getPerformanceReviewsByEmployeeId(employeeId);
        return ResponseEntity.ok(reviewsByEmployee);
    }

    @GetMapping("/test")
    private String test() {
        return "Working";
    }
}
