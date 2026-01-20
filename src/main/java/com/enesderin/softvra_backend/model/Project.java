package com.enesderin.softvra_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;


    @Column(name = "image_url",columnDefinition = "LONGTEXT")
    private String imageUrl;

    private String link;


    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
