package com.FedEx.project_service.service.Impl;
import com.FedEx.project_service.dto.ProjectRequestDTO;
import com.FedEx.project_service.dto.ProjectResponseDTO;
import com.FedEx.project_service.entity.Project;
import com.FedEx.project_service.repository.ProjectRepository;
import com.FedEx.project_service.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ProjectResponseDTO getProjectDetails(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return mapProjectToResponseDTO(project);
    }

    @Override
    public ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO) {
        Project project = mapRequestDTOToProject(projectRequestDTO);
        Project savedProject = projectRepository.save(project);
        return mapProjectToResponseDTO(savedProject);
    }

    @Override
    public List<ProjectResponseDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();

        return projects.stream()
                .map(this::mapProjectToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponseDTO updateProject(Long projectId, ProjectRequestDTO projectRequestDTO) {
        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Update fields
        existingProject.setName(projectRequestDTO.getName());
        existingProject.setDescription(projectRequestDTO.getDescription());
        existingProject.setStartDate(projectRequestDTO.getStartDate());
        existingProject.setEndDate(projectRequestDTO.getEndDate());
        existingProject.setProjectManagerId(projectRequestDTO.getProjectManagerId());

        Project updatedProject = projectRepository.save(existingProject);
        return mapProjectToResponseDTO(updatedProject);
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    // Helper method to map Project entity to ProjectResponseDTO
    private ProjectResponseDTO mapProjectToResponseDTO(Project project) {
        return objectMapper.convertValue(project, ProjectResponseDTO.class);
    }

    // Helper method to map ProjectRequestDTO to Project entity
    private Project mapRequestDTOToProject(ProjectRequestDTO projectRequestDTO) {
        return objectMapper.convertValue(projectRequestDTO, Project.class);
    }
}
