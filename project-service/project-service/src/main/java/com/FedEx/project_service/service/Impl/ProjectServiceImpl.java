package com.FedEx.project_service.service.Impl;
//import com.FedEx.project_service.JMS.NotificationProducer;
import com.FedEx.project_service.Exceptions.ProjectNotFoundException;
import com.FedEx.project_service.JMS.JmsProducer;
import com.FedEx.project_service.dto.ProjectRequestDTO;
import com.FedEx.project_service.dto.ProjectResponseDTO;
import com.FedEx.project_service.entity.EmployeeProject;
import com.FedEx.project_service.entity.NotificationMessage;
import com.FedEx.project_service.entity.Project;
import com.FedEx.project_service.repository.EmployeeProjectRepository;
import com.FedEx.project_service.repository.ProjectRepository;
import com.FedEx.project_service.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import com.FedEx.project_service.entity.EmployeeResponseDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeProjectRepository employeeProjectRepository;

    @Autowired
    private RestTemplate restTemplate; // Ensure RestTemplate is configured in your application

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private JmsProducer jmsProducer;

    String baseUrl = "http://localhost:8091/employees";
//    @Value("${http://localhost:8091/employees}")
//    //http://localhost:8091/employees
//    private String employeeServiceUrl; // URL of Employee Service

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

        Project updatedProject = projectRepository.save(existingProject);
        return mapProjectToResponseDTO(updatedProject);
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    @Override
    @Transactional
    public void assignEmployeesToProject(Long projectId, List<Long> employeeIds) {
        List<Long> currentEmployeeIds = getEmployeeIdsByProjectId(projectId);

        List<Long> employeesToAdd = employeeIds.stream()
                .filter(id -> !currentEmployeeIds.contains(id))
                .collect(Collectors.toList());

        List<Long> employeesToRemove = currentEmployeeIds.stream()
                .filter(id -> !employeeIds.contains(id))
                .collect(Collectors.toList());

        String projectName = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId)).getName();
        if(projectName == null) {
            return;
        }
        // Add new employees
        for (Long employeeId : employeesToAdd) {
            EmployeeProject assignment = new EmployeeProject();
            assignment.setEmployeeId(employeeId);
            assignment.setProjectId(projectId);
            employeeProjectRepository.save(assignment);

            // Send notification
            sendNotification(projectName, projectId, "assigned", employeeId);
            System.out.println("Sent message: ");
        }

        // Remove de-selected employees
        for (Long employeeId : employeesToRemove) {
            employeeProjectRepository.deleteByProjectIdAndEmployeeId(projectId, employeeId);

            // Send notification
            sendNotification(projectName, projectId, "deassigned", employeeId);
        }
    }

    private void sendNotification(String projectName, Long projectId, String status, Long employeeId) {
        NotificationMessage message = new NotificationMessage();
        message.setProjectName(projectName);
        message.setProjectId(projectId);
        message.setStatus(status);
        message.setEmployeeIds(List.of(employeeId));
        jmsTemplate.convertAndSend("notificationQueue", message);
    }

    @Override
    public List<Long> getEmployeeIdsByProjectId(Long projectId) {
        List<EmployeeProject> assignments = employeeProjectRepository.findByProjectId(projectId);
        return assignments.stream().map(EmployeeProject::getEmployeeId).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponseDTO> getEmployeesDetailsByProjectId(Long projectId) {
        List<Long> employeeIds = getEmployeeIdsByProjectId(projectId);
        List<EmployeeResponseDTO> employees = new ArrayList<>();

        for (Long employeeId : employeeIds) {
            EmployeeResponseDTO employee = restTemplate.getForObject(baseUrl + "/" +employeeId, EmployeeResponseDTO.class);
            if (employee != null) {
                employees.add(employee);
            }
        }

        return employees;
    }

    @Override
    public List<Long> getProjectIdsByEmployeeId(Long employeeId) {
        List<EmployeeProject> assignments = employeeProjectRepository.findByEmployeeId(employeeId);
        return assignments.stream().map(EmployeeProject::getProjectId).collect(Collectors.toList());
    }

    @Override
    public List<ProjectResponseDTO> getProjectsDetailsByEmployeeId(Long employeeId) {
        List<Long> projectIds = getProjectIdsByEmployeeId(employeeId);
        List<ProjectResponseDTO> projects = new ArrayList<>();

        for (Long projectId : projectIds) {
            ProjectResponseDTO project = getProjectDetails(projectId);
            if (project != null) {
                projects.add(project);
            }
        }

        return projects;
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
