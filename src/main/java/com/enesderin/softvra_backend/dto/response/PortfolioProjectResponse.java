// PortfolioProjectResponse.java
package com.enesderin.softvra_backend.dto.response;

import com.enesderin.softvra_backend.model.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioProjectResponse {

    private Long id;
    private String category;
    private String title;
    private String projectType;
    private String description;
    private String problemStatement;
    private String solutionStatement;
    private String backgroundGradientA;
    private String backgroundGradientB;
    private String liveUrl;
    private String imageUrl;
    private Boolean isFeatured;
    private Integer displayOrder;
    private ProjectStatus status;
    private List<ProjectResultResponse> results;
    private List<ProjectFeatureResponsePortfolio> features;
    private List<ProjectTechStackResponse> techStack;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}