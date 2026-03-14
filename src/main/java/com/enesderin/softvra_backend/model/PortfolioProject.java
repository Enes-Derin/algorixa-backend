// PortfolioProject.java
package com.enesderin.softvra_backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "portfolio_projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String category; // 'Sağlık','Hukuk','Fintech' etc.

    @Column(nullable = false, length = 300)
    private String title;

    @Column(name = "project_type", length = 200)
    private String projectType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "problem_statement", columnDefinition = "TEXT")
    private String problemStatement;

    @Column(name = "solution_statement", columnDefinition = "TEXT")
    private String solutionStatement;

    @Column(name = "background_gradient_a", length = 100)
    private String backgroundGradientA;

    @Column(name = "background_gradient_b", length = 100)
    private String backgroundGradientB;

    @Column(name = "image_url", columnDefinition = "LONGTEXT")
    private String imageUrl;

    @Column(name = "live_url", length = 500)
    private String liveUrl;

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ProjectStatus status = ProjectStatus.PUBLISHED;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProjectResult> results = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProjectFeature> projectFeatures = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProjectTechStack> techStack = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Helper methods
    public void addResult(ProjectResult result) {
        results.add(result);
        result.setProject(this);
    }

    public void addProjectFeature(ProjectFeature feature) {
        projectFeatures.add(feature);
        feature.setProject(this);
    }

    public void addTechStack(ProjectTechStack tech) {
        techStack.add(tech);
        tech.setProject(this);
    }
}