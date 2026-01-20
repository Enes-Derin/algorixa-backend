package com.enesderin.softvra_backend.controller;

import com.enesderin.softvra_backend.dto.request.ProjectRequest;
import com.enesderin.softvra_backend.dto.response.ProjectResponse;

import java.util.List;

public interface ProjectController {

    RootEntity<List<ProjectResponse>> getAllProject();
    RootEntity<ProjectResponse> createProject(ProjectRequest projectRequest);
    RootEntity<ProjectResponse> updateProject(Long id, ProjectRequest projectRequest);
    RootEntity<String> deleteProject(Long id);
}
