package com.FedEx.project_service.controller;

import com.FedEx.project_service.dto.ProjectRequestDTO;
import com.FedEx.project_service.dto.ProjectResponseDTO;
import com.FedEx.project_service.entity.EmployeeResponseDTO;
import com.FedEx.project_service.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/test")
    public String test() {
        return "Working";
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> getProjectDetails(@PathVariable Long projectId) {
        ProjectResponseDTO projectResponseDTO = projectService.getProjectDetails(projectId);
        return ResponseEntity.ok(projectResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        List<ProjectResponseDTO> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectRequestDTO projectRequestDTO) {
        ProjectResponseDTO createdProject = projectService.createProject(projectRequestDTO);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long projectId,
                                                            @RequestBody ProjectRequestDTO projectRequestDTO) {
        ProjectResponseDTO updatedProject = projectService.updateProject(projectId, projectRequestDTO);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{projectId}/assign")
    public ResponseEntity<?> assignEmployeesToProject(@PathVariable Long projectId, @RequestBody List<Long> employeeIds) {
        projectService.assignEmployeesToProject(projectId, employeeIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{projectId}/employees")
    public List<Long> getEmployeesByProjectId(@PathVariable Long projectId) {
        return projectService.getEmployeeIdsByProjectId(projectId);
    }

    @GetMapping("/{projectId}/employees/details")
    public List<EmployeeResponseDTO> getEmployeesDetailsByProjectId(@PathVariable Long projectId) {
        return projectService.getEmployeesDetailsByProjectId(projectId);
    }

    @GetMapping("{employeeId}/projects")
    public List<Long> getProjectsByEmployeeId(@PathVariable Long employeeId) {
        return projectService.getProjectIdsByEmployeeId(employeeId);
    }

    @GetMapping("/{employeeId}/projects/details")
    public List<ProjectResponseDTO> getProjectsDetailsByEmployeeId(@PathVariable Long employeeId) {
        return projectService.getProjectsDetailsByEmployeeId(employeeId);
    }
}