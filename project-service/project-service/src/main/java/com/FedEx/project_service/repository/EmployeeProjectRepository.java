package com.FedEx.project_service.repository;

import com.FedEx.project_service.entity.EmployeeProject;
import com.FedEx.project_service.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Long> {
    List<EmployeeProject> findByProjectId(Long projectId);

    @Modifying
    @Query("DELETE FROM EmployeeProject ep WHERE ep.projectId = :projectId AND ep.employeeId = :employeeId")
    void deleteByProjectIdAndEmployeeId(@Param("projectId") Long projectId, @Param("employeeId") Long employeeId);

    List<EmployeeProject> findByEmployeeId(Long employeeId);
}
