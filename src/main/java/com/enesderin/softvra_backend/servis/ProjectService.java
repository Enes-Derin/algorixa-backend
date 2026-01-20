package com.enesderin.softvra_backend.servis;

import com.enesderin.softvra_backend.dto.request.ProjectRequest;
import com.enesderin.softvra_backend.dto.response.ProjectResponse;

import java.util.List;

public interface ProjectService {
    List<ProjectResponse> getAllProject();
    ProjectResponse createProject(ProjectRequest projectRequest);
    ProjectResponse updateProject(Long id, ProjectRequest projectRequest);
    void deleteProject(Long id);
}
