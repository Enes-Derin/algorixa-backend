// PortfolioService.java
package com.enesderin.softvra_backend.servis;

import com.enesderin.softvra_backend.dto.request.PortfolioProjectRequest;
import com.enesderin.softvra_backend.dto.response.PortfolioProjectResponse;

import java.util.List;
import java.util.Optional;

public interface PortfolioService {

    // Public
    List<PortfolioProjectResponse> getPublishedProjects();
    List<PortfolioProjectResponse> getProjectsByCategory(String category);
    Optional<PortfolioProjectResponse> getFeaturedProject();
    PortfolioProjectResponse getProjectById(Long id);
    List<String> getAllCategories();

    // Admin
    List<PortfolioProjectResponse> getAllProjects();
    PortfolioProjectResponse createProject(PortfolioProjectRequest request);
    PortfolioProjectResponse updateProject(Long id, PortfolioProjectRequest request);
    void deleteProject(Long id);
    PortfolioProjectResponse toggleFeatured(Long id);
    PortfolioProjectResponse updateDisplayOrder(Long id, Integer order);
}