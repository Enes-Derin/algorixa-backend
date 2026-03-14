// PortfolioController.java
package com.enesderin.softvra_backend.controller;

import com.enesderin.softvra_backend.dto.request.PortfolioProjectRequest;
import com.enesderin.softvra_backend.dto.response.PortfolioProjectResponse;

import java.util.List;

public interface PortfolioController {

    // Public
    RootEntity<List<PortfolioProjectResponse>> getPublishedProjects();
    RootEntity<List<PortfolioProjectResponse>> getProjectsByCategory(String category);
    RootEntity<PortfolioProjectResponse> getFeaturedProject();
    RootEntity<PortfolioProjectResponse> getProjectById(Long id);
    RootEntity<List<String>> getAllCategories();

    // Admin
    RootEntity<List<PortfolioProjectResponse>> getAllProjects();
    RootEntity<PortfolioProjectResponse> createProject(PortfolioProjectRequest request);
    RootEntity<PortfolioProjectResponse> updateProject(Long id, PortfolioProjectRequest request);
    RootEntity<String> deleteProject(Long id);
    RootEntity<PortfolioProjectResponse> toggleFeatured(Long id);
    RootEntity<PortfolioProjectResponse> updateDisplayOrder(Long id, Integer order);
}