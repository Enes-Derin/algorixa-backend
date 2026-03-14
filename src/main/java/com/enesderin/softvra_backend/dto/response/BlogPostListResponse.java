// BlogPostListResponse.java (Hafif liste için)
package com.enesderin.softvra_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogPostListResponse {
    private Long id;
    private String slug;
    private String title;
    private String excerpt;
    private String category;
    private String author;
    private String readTime;
    private LocalDate publishedDate;
    private Boolean isFeatured;
    private Integer viewCount;
    private String imageUrl;
}