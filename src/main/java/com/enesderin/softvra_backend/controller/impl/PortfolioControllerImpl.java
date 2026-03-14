package com.enesderin.softvra_backend.controller.impl;

import com.enesderin.softvra_backend.controller.PortfolioController;
import com.enesderin.softvra_backend.controller.RestBaseController;
import com.enesderin.softvra_backend.controller.RootEntity;
import com.enesderin.softvra_backend.dto.request.PortfolioProjectRequest;
import com.enesderin.softvra_backend.dto.response.PortfolioProjectResponse;
import com.enesderin.softvra_backend.servis.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PortfolioControllerImpl extends RestBaseController implements PortfolioController {

    private final PortfolioService portfolioService;

    // ── PUBLIC ──────────────────────────────────────────────

    @GetMapping("/public/portfolio/projects")
    @Override
    public RootEntity<List<PortfolioProjectResponse>> getPublishedProjects() {
        return ok(portfolioService.getPublishedProjects());
    }

    @GetMapping("/public/portfolio/projects/category/{category}")
    @Override
    public RootEntity<List<PortfolioProjectResponse>> getProjectsByCategory(@PathVariable String category) {
        return ok(portfolioService.getProjectsByCategory(category));
    }

    @GetMapping("/public/portfolio/featured")
    @Override
    public RootEntity<PortfolioProjectResponse> getFeaturedProject() {
        return portfolioService.getFeaturedProject()
                .map(this::ok)
                .orElse(ok(null));
    }

    @GetMapping("/public/portfolio/projects/{id}")
    @Override
    public RootEntity<PortfolioProjectResponse> getProjectById(@PathVariable Long id) {
        return ok(portfolioService.getProjectById(id));
    }

    @GetMapping("/public/portfolio/categories")
    @Override
    public RootEntity<List<String>> getAllCategories() {
        return ok(portfolioService.getAllCategories());
    }

    // ── ADMIN ────────────────────────────────────────────────

    @GetMapping("/admin/portfolio/projects")
    @Override
    public RootEntity<List<PortfolioProjectResponse>> getAllProjects() {
        return ok(portfolioService.getAllProjects());
    }

    @PostMapping(value = "/admin/portfolio/projects", consumes = "multipart/form-data")
    @Override
    public RootEntity<PortfolioProjectResponse> createProject(
            @ModelAttribute PortfolioProjectRequest request) {
        return ok(portfolioService.createProject(request));
    }

    @PutMapping(value = "/admin/portfolio/projects/{id}", consumes = "multipart/form-data")
    @Override
    public RootEntity<PortfolioProjectResponse> updateProject(
            @PathVariable Long id,
            @ModelAttribute PortfolioProjectRequest request) {
        return ok(portfolioService.updateProject(id, request));
    }

    @DeleteMapping("/admin/portfolio/projects/{id}")
    @Override
    public RootEntity<String> deleteProject(@PathVariable Long id) {
        portfolioService.deleteProject(id);
        return ok("Portfolio project deleted successfully");
    }

    @PatchMapping("/admin/portfolio/projects/{id}/featured")
    @Override
    public RootEntity<PortfolioProjectResponse> toggleFeatured(@PathVariable Long id) {
        return ok(portfolioService.toggleFeatured(id));
    }

    @PatchMapping("/admin/portfolio/projects/{id}/order")
    @Override
    public RootEntity<PortfolioProjectResponse> updateDisplayOrder(
            @PathVariable Long id,
            @RequestParam Integer order) {
        return ok(portfolioService.updateDisplayOrder(id, order));
    }
}