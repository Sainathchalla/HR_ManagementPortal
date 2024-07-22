package com.FedEx.project_service.repository;

import com.FedEx.project_service.entity.NotificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationMessageRepository extends JpaRepository<NotificationMessage, Long> {
    List<NotificationMessage> findByEmployeeIdsContains(Long employeeId);
}
