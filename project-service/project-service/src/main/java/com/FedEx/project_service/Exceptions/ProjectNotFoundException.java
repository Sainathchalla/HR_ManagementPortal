package com.FedEx.project_service.Exceptions;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(Long projectId) {
        super("Project with ID " + projectId + " not found");
    }
}
