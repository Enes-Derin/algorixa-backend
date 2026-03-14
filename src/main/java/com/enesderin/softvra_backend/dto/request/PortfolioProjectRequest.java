package com.enesderin.softvra_backend.dto.request;

import com.enesderin.softvra_backend.model.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioProjectRequest {

    @NotBlank(message = "Kategori gereklidir")
    private String category;

    @NotBlank(message = "Başlık gereklidir")
    private String title;

    private String projectType;

    @NotBlank(message = "Açıklama gereklidir")
    private String description;

    private String problemStatement;
    private String solutionStatement;
    private String backgroundGradientA;
    private String backgroundGradientB;

    private MultipartFile imageUrl;
    private String liveUrl;

    private Boolean isFeatured = false;
    private Integer displayOrder = 0;
    private ProjectStatus status = ProjectStatus.PUBLISHED;

    // Nested list'ler — @ModelAttribute ile bind edilemiyor,
    // frontend'den JSON string olarak gelir, service'te parse edilir
    private String resultsJson;
    private String featuresJson;
    private String techStackJson;

    // Bu field'lar artık kullanılmıyor (JSON string versiyonları var)
    // Ama geriye dönük uyumluluk için bırakıyoruz
    private List<ProjectResultRequest> results = new ArrayList<>();
    private List<ProjectFeatureRequest> features = new ArrayList<>();
    private List<ProjectTechStackRequest> techStack = new ArrayList<>();
}