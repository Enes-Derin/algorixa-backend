package com.enesderin.softvra_backend.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse {
    private Long id;

    private String title;

    private String description;

    private String imageUrl;

    private String link;

    private LocalDateTime createdAt;
}
