// BlogPostResponse.java
package com.enesderin.softvra_backend.dto.response;

import com.enesderin.softvra_backend.model.BlogStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogPostResponse {

    private Long id;
    private String slug;
    private String title;
    private String excerpt;
    private String content;
    private String category;
    private String author;
    private String readTime;
    private LocalDate publishedDate;
    private Boolean isFeatured;
    private Integer viewCount;
    private String metaTitle;
    private String metaDescription;
    private BlogStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String imageUrl;
}