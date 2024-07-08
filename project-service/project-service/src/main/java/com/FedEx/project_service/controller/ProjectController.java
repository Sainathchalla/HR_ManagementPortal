package com.FedEx.project_service.controller;

import com.FedEx.project_service.dto.ProjectRequestDTO;
import com.FedEx.project_service.dto.ProjectResponseDTO;
import com.FedEx.project_service.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
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

//    @PostMapping("/{projectId}/assign")
//    public ResponseEntity<Void> assignEmployeeToProject(@PathVariable Long projectId,
//                                                        @RequestBody EmployeeAssignmentDTO employeeAssignmentDTO) {
//        projectService.assignEmployeeToProject(projectId, employeeAssignmentDTO);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/{projectId}/employees")
//    public ResponseEntity<List<EmployeeAssignmentDTO>> getEmployeesAssignedToProject(@PathVariable Long projectId) {
//        List<EmployeeAssignmentDTO> employees = projectService.getEmployeesAssignedToProject(projectId);
//        return ResponseEntity.ok(employees);
//    }
}