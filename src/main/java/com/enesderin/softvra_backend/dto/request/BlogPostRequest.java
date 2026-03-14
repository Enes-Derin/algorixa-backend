// BlogPostRequest.java
package com.enesderin.softvra_backend.dto.request;

import com.enesderin.softvra_backend.model.BlogStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogPostRequest {

    @NotBlank(message = "Slug gereklidir")
    private String slug;

    @NotBlank(message = "Başlık gereklidir")
    private String title;

    private String excerpt;

    @NotBlank(message = "İçerik gereklidir")
    private String content;

    @NotBlank(message = "Kategori gereklidir")
    private String category;

    private String author = "Algorixa";

    private String readTime;

    @NotNull(message = "Yayın tarihi gereklidir")
    private LocalDate publishedDate;

    private Boolean isFeatured = false;

    private String metaTitle;

    private String metaDescription;

    private BlogStatus status = BlogStatus.DRAFT;

    private MultipartFile imageUrl;
}