package com.enesderin.softvra_backend.servis.impl;

import com.enesderin.softvra_backend.dto.request.ProjectRequest;
import com.enesderin.softvra_backend.dto.response.ProjectResponse;
import com.enesderin.softvra_backend.exception.BaseException;
import com.enesderin.softvra_backend.exception.ErrorMessage;
import com.enesderin.softvra_backend.exception.MessageType;
import com.enesderin.softvra_backend.model.Project;
import com.enesderin.softvra_backend.repo.ProjectRepository;
import com.enesderin.softvra_backend.servis.CloudinaryService;
import com.enesderin.softvra_backend.servis.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private CloudinaryService cloudinaryService;

    @Override
    public List<ProjectResponse> getAllProject() {
        List<Project> projectRepositoryAll = this.projectRepository.findAll();
        List<ProjectResponse> projectResponseList = new ArrayList<>();
        for (Project project : projectRepositoryAll) {
            ProjectResponse projectResponse = new ProjectResponse();
            projectResponse.setId(project.getId());
            projectResponse.setTitle(project.getTitle());
            projectResponse.setDescription(project.getDescription());
            projectResponse.setImageUrl(project.getImageUrl());
            projectResponse.setCreatedAt(project.getCreatedAt());
            projectResponseList.add(projectResponse);
        }
        return projectResponseList;
    }

    @Override
    public ProjectResponse createProject(ProjectRequest projectRequest) {
        Project project = new Project();
        project.setTitle(projectRequest.getTitle());
        project.setDescription(projectRequest.getDescription());
        project.setCreatedAt(LocalDateTime.now());

        if (projectRequest.getImageUrl() != null && !projectRequest.getImageUrl().isEmpty()) {
            try {
                String base64Image = Base64.getEncoder()
                        .encodeToString(projectRequest.getImageUrl().getBytes());

                String imageUrl = cloudinaryService.uploadSignature(
                        "data:image/png;base64," + base64Image,
                        "projects"
                );

                project.setImageUrl(imageUrl);
            } catch (Exception e) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Image upload failed: " + e.getMessage()));
            }

        }
        Project saved = projectRepository.save(project);

        return new ProjectResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getImageUrl(),
                saved.getLink(),
                saved.getCreatedAt()
        );
    }

        @Override
        public ProjectResponse updateProject (Long id, ProjectRequest projectRequest){

            Project project = projectRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Project not found"));

            project.setTitle(projectRequest.getTitle());
            project.setDescription(projectRequest.getDescription());
            project.setCreatedAt(LocalDateTime.now());

            if (projectRequest.getImageUrl() != null && !projectRequest.getImageUrl().isEmpty()) {

                // eski görseli sil
                cloudinaryService.deleteByUrl(project.getImageUrl());

                try {
                    String base64Image = Base64.getEncoder()
                            .encodeToString(projectRequest.getImageUrl().getBytes());

                    String imageUrl = cloudinaryService.uploadSignature(
                            "data:image/png;base64," + base64Image,
                            "projects"
                    );

                    project.setImageUrl(imageUrl);
                } catch (Exception e) {
                    throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Image upload failed: " + e.getMessage()));
                }
            }

            Project saved = projectRepository.save(project);

            return new ProjectResponse(
                    saved.getId(),
                    saved.getTitle(),
                    saved.getDescription(),
                    saved.getImageUrl(),
                    saved.getLink(),
                    saved.getCreatedAt()
            );
        }

        @Override
        public void deleteProject (Long id){
            Project project = projectRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Project not found"));

            cloudinaryService.deleteByUrl(project.getImageUrl());
            projectRepository.deleteById(id);
        }
    }

