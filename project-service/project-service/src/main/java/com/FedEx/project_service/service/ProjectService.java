package com.FedEx.project_service.service;

import com.FedEx.project_service.dto.ProjectRequestDTO;
import com.FedEx.project_service.dto.ProjectResponseDTO;
import com.FedEx.project_service.entity.EmployeeResponseDTO;

import java.util.List;

public interface ProjectService {
    ProjectResponseDTO getProjectDetails(Long projectId);

    ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO);

    ProjectResponseDTO updateProject(Long projectId, ProjectRequestDTO projectRequestDTO);

    void deleteProject(Long projectId);

    List<ProjectResponseDTO> getAllProjects();

    void assignEmployeesToProject(Long projectId, List<Long> employeeIds);

    List<Long> getEmployeeIdsByProjectId(Long projectId);

    List<EmployeeResponseDTO> getEmployeesDetailsByProjectId(Long projectId);

    List<Long> getProjectIdsByEmployeeId(Long employeeId);

    List<ProjectResponseDTO> getProjectsDetailsByEmployeeId(Long employeeId);
}
