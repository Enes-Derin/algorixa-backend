package com.enesderin.softvra_backend.servis.impl;

import com.enesderin.softvra_backend.dto.request.PortfolioProjectRequest;
import com.enesderin.softvra_backend.dto.request.ProjectFeatureRequest;
import com.enesderin.softvra_backend.dto.request.ProjectResultRequest;
import com.enesderin.softvra_backend.dto.request.ProjectTechStackRequest;
import com.enesderin.softvra_backend.dto.response.PortfolioProjectResponse;
import com.enesderin.softvra_backend.dto.response.ProjectFeatureResponsePortfolio;
import com.enesderin.softvra_backend.dto.response.ProjectResultResponse;
import com.enesderin.softvra_backend.dto.response.ProjectTechStackResponse;
import com.enesderin.softvra_backend.exception.BaseException;
import com.enesderin.softvra_backend.exception.ErrorMessage;
import com.enesderin.softvra_backend.exception.MessageType;
import com.enesderin.softvra_backend.model.*;
import com.enesderin.softvra_backend.repo.PortfolioProjectRepository;
import com.enesderin.softvra_backend.repo.ProjectFeatureRepository;
import com.enesderin.softvra_backend.repo.ProjectResultRepository;
import com.enesderin.softvra_backend.repo.ProjectTechStackRepository;
import com.enesderin.softvra_backend.servis.CloudinaryService;
import com.enesderin.softvra_backend.servis.PortfolioService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioProjectRepository projectRepository;
    private final ProjectResultRepository resultRepository;
    private final ProjectFeatureRepository featureRepository;
    private final ProjectTechStackRepository techStackRepository;
    private final CloudinaryService cloudinaryService;
    private final ObjectMapper objectMapper;

    // ── JSON string → typed list parse yardımcıları ──────────────

    private List<ProjectResultRequest> parseResults(PortfolioProjectRequest request) {
        if (request.getResultsJson() != null && !request.getResultsJson().isBlank()) {
            try {
                return objectMapper.readValue(request.getResultsJson(),
                        new TypeReference<List<ProjectResultRequest>>() {});
            } catch (Exception e) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
                        "Sonuçlar parse edilemedi: " + e.getMessage()));
            }
        }
        return request.getResults() != null ? request.getResults() : new ArrayList<>();
    }

    private List<ProjectFeatureRequest> parseFeatures(PortfolioProjectRequest request) {
        if (request.getFeaturesJson() != null && !request.getFeaturesJson().isBlank()) {
            try {
                return objectMapper.readValue(request.getFeaturesJson(),
                        new TypeReference<List<ProjectFeatureRequest>>() {});
            } catch (Exception e) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
                        "Özellikler parse edilemedi: " + e.getMessage()));
            }
        }
        return request.getFeatures() != null ? request.getFeatures() : new ArrayList<>();
    }

    private List<ProjectTechStackRequest> parseTechStack(PortfolioProjectRequest request) {
        if (request.getTechStackJson() != null && !request.getTechStackJson().isBlank()) {
            try {
                return objectMapper.readValue(request.getTechStackJson(),
                        new TypeReference<List<ProjectTechStackRequest>>() {});
            } catch (Exception e) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
                        "Teknoloji stack parse edilemedi: " + e.getMessage()));
            }
        }
        return request.getTechStack() != null ? request.getTechStack() : new ArrayList<>();
    }

    // ── Public endpoints ─────────────────────────────────────────

    @Override
    public List<PortfolioProjectResponse> getPublishedProjects() {
        return projectRepository.findByStatusOrderByDisplayOrderAsc(ProjectStatus.PUBLISHED)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<PortfolioProjectResponse> getProjectsByCategory(String category) {
        return projectRepository.findByStatusAndCategoryOrderByDisplayOrderAsc(ProjectStatus.PUBLISHED, category)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public Optional<PortfolioProjectResponse> getFeaturedProject() {
        return projectRepository.findByIsFeaturedTrueAndStatus(ProjectStatus.PUBLISHED)
                .map(this::toResponse);
    }

    @Override
    public PortfolioProjectResponse getProjectById(Long id) {
        PortfolioProject project = projectRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Proje bulunamadı: " + id)));
        return toResponse(project);
    }

    @Override
    public List<String> getAllCategories() {
        return Arrays.asList("Tümü", "Kurumsal", "E-Ticaret", "Sağlık", "Hukuk", "Fintech", "Eğitim");
    }

    @Override
    public List<PortfolioProjectResponse> getAllProjects() {
        return projectRepository.findAllByOrderByDisplayOrderAsc()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ── Admin CRUD ────────────────────────────────────────────────

    @Override
    @Transactional
    public PortfolioProjectResponse createProject(PortfolioProjectRequest request) {

        // Görsel yükle
        String imageUrl = null;
        if (request.getImageUrl() != null && !request.getImageUrl().isEmpty()) {
            try {
                String base64Image = Base64.getEncoder()
                        .encodeToString(request.getImageUrl().getBytes());
                imageUrl = cloudinaryService.uploadSignature(
                        "data:image/png;base64," + base64Image,
                        "portfolio-" + slugify(request.getTitle())
                );
            } catch (Exception e) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
                        "Görsel yüklenemedi: " + e.getMessage()));
            }
        }

        PortfolioProject project = PortfolioProject.builder()
                .category(request.getCategory())
                .title(request.getTitle())
                .projectType(request.getProjectType())
                .description(request.getDescription())
                .problemStatement(request.getProblemStatement())
                .solutionStatement(request.getSolutionStatement())
                .backgroundGradientA(request.getBackgroundGradientA())
                .backgroundGradientB(request.getBackgroundGradientB())
                .imageUrl(imageUrl)
                .liveUrl(request.getLiveUrl())
                .isFeatured(request.getIsFeatured())
                .displayOrder(request.getDisplayOrder())
                .status(request.getStatus())
                .build();

        PortfolioProject savedProject = projectRepository.save(project);

        // JSON parse et ve ekle
        parseResults(request).forEach(r -> savedProject.addResult(
                ProjectResult.builder().project(savedProject)
                        .resultText(r.getResultText()).displayOrder(r.getDisplayOrder()).build()
        ));
        parseFeatures(request).forEach(f -> savedProject.addProjectFeature(
                ProjectFeature.builder().project(savedProject)
                        .featureText(f.getFeatureText()).displayOrder(f.getDisplayOrder()).build()
        ));
        parseTechStack(request).forEach(t -> savedProject.addTechStack(
                ProjectTechStack.builder().project(savedProject)
                        .technology(t.getTechnology()).displayOrder(t.getDisplayOrder()).build()
        ));

        return toResponse(projectRepository.save(savedProject));
    }

    @Override
    @Transactional
    public PortfolioProjectResponse updateProject(Long id, PortfolioProjectRequest request) {
        PortfolioProject project = projectRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Proje bulunamadı: " + id)));

        // Görsel güncelle
        if (request.getImageUrl() != null && !request.getImageUrl().isEmpty()) {
            if (project.getImageUrl() != null) {
                cloudinaryService.deleteByUrl(project.getImageUrl());
            }
            try {
                String base64Image = Base64.getEncoder()
                        .encodeToString(request.getImageUrl().getBytes());
                project.setImageUrl(cloudinaryService.uploadSignature(
                        "data:image/png;base64," + base64Image,
                        "portfolio-" + slugify(request.getTitle())
                ));
            } catch (Exception e) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
                        "Görsel yüklenemedi: " + e.getMessage()));
            }
        }

        project.setCategory(request.getCategory());
        project.setTitle(request.getTitle());
        project.setProjectType(request.getProjectType());
        project.setDescription(request.getDescription());
        project.setProblemStatement(request.getProblemStatement());
        project.setLiveUrl(request.getLiveUrl());
        project.setSolutionStatement(request.getSolutionStatement());
        project.setBackgroundGradientA(request.getBackgroundGradientA());
        project.setBackgroundGradientB(request.getBackgroundGradientB());
        project.setIsFeatured(request.getIsFeatured());
        project.setDisplayOrder(request.getDisplayOrder());
        project.setStatus(request.getStatus());

        // İlişkili verileri temizle ve yeniden yaz
        resultRepository.deleteByProjectId(id);
        featureRepository.deleteByProjectId(id);
        techStackRepository.deleteByProjectId(id);
        project.getResults().clear();
        project.getProjectFeatures().clear();
        project.getTechStack().clear();

        parseResults(request).forEach(r -> project.addResult(
                ProjectResult.builder().project(project)
                        .resultText(r.getResultText()).displayOrder(r.getDisplayOrder()).build()
        ));
        parseFeatures(request).forEach(f -> project.addProjectFeature(
                ProjectFeature.builder().project(project)
                        .featureText(f.getFeatureText()).displayOrder(f.getDisplayOrder()).build()
        ));
        parseTechStack(request).forEach(t -> project.addTechStack(
                ProjectTechStack.builder().project(project)
                        .technology(t.getTechnology()).displayOrder(t.getDisplayOrder()).build()
        ));

        return toResponse(projectRepository.save(project));
    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        PortfolioProject project = projectRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Proje bulunamadı: " + id)));
        if (project.getImageUrl() != null) {
            cloudinaryService.deleteByUrl(project.getImageUrl());
        }
        projectRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PortfolioProjectResponse toggleFeatured(Long id) {
        PortfolioProject project = projectRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Proje bulunamadı: " + id)));
        project.setIsFeatured(!project.getIsFeatured());
        return toResponse(projectRepository.save(project));
    }

    @Override
    @Transactional
    public PortfolioProjectResponse updateDisplayOrder(Long id, Integer order) {
        PortfolioProject project = projectRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Proje bulunamadı: " + id)));
        project.setDisplayOrder(order);
        return toResponse(projectRepository.save(project));
    }

    // ── Yardımcılar ───────────────────────────────────────────────

    private String slugify(String text) {
        if (text == null) return "project";
        return text.toLowerCase()
                .replace("ğ", "g").replace("ü", "u").replace("ş", "s")
                .replace("ı", "i").replace("ö", "o").replace("ç", "c")
                .replaceAll("[^a-z0-9]+", "-").replaceAll("^-+|-+$", "");
    }

    private PortfolioProjectResponse toResponse(PortfolioProject project) {
        return PortfolioProjectResponse.builder()
                .id(project.getId())
                .category(project.getCategory())
                .title(project.getTitle())
                .projectType(project.getProjectType())
                .description(project.getDescription())
                .problemStatement(project.getProblemStatement())
                .liveUrl(project.getLiveUrl())
                .solutionStatement(project.getSolutionStatement())
                .backgroundGradientA(project.getBackgroundGradientA())
                .backgroundGradientB(project.getBackgroundGradientB())
                .imageUrl(project.getImageUrl())
                .isFeatured(project.getIsFeatured())
                .displayOrder(project.getDisplayOrder())
                .status(project.getStatus())
                .results(project.getResults() != null ? project.getResults().stream()
                        .map(r -> ProjectResultResponse.builder()
                                .id(r.getId()).resultText(r.getResultText())
                                .displayOrder(r.getDisplayOrder()).build())
                        .collect(Collectors.toList()) : new ArrayList<>())
                .features(project.getProjectFeatures() != null ? project.getProjectFeatures().stream()
                        .map(f -> ProjectFeatureResponsePortfolio.builder()
                                .id(f.getId()).featureText(f.getFeatureText())
                                .displayOrder(f.getDisplayOrder()).build())
                        .collect(Collectors.toList()) : new ArrayList<>())
                .techStack(project.getTechStack() != null ? project.getTechStack().stream()
                        .map(t -> ProjectTechStackResponse.builder()
                                .id(t.getId()).technology(t.getTechnology())
                                .displayOrder(t.getDisplayOrder()).build())
                        .collect(Collectors.toList()) : new ArrayList<>())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}