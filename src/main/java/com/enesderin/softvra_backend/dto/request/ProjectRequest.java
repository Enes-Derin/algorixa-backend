package com.enesderin.softvra_backend.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private MultipartFile imageUrl;

    private String link;

    private LocalDateTime createdAt;
}
