package com.enesderin.softvra_backend.servis.impl;

import com.enesderin.softvra_backend.dto.request.BlogPostRequest;
import com.enesderin.softvra_backend.dto.response.BlogPostListResponse;
import com.enesderin.softvra_backend.dto.response.BlogPostResponse;
import com.enesderin.softvra_backend.exception.BaseException;
import com.enesderin.softvra_backend.exception.ErrorMessage;
import com.enesderin.softvra_backend.exception.MessageType;
import com.enesderin.softvra_backend.model.BlogPost;
import com.enesderin.softvra_backend.model.BlogStatus;
import com.enesderin.softvra_backend.repo.BlogPostRepository;
import com.enesderin.softvra_backend.servis.BlogService;
import com.enesderin.softvra_backend.servis.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogPostRepository blogPostRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<BlogPostListResponse> getAllPublishedPosts() {
        return blogPostRepository.findByStatusOrderByPublishedDateDesc(BlogStatus.PUBLISHED)
                .stream().map(this::toListResponse).collect(Collectors.toList());
    }

    @Override
    public List<BlogPostListResponse> getPostsByCategory(String category) {
        return blogPostRepository.findByStatusAndCategoryOrderByPublishedDateDesc(BlogStatus.PUBLISHED, category)
                .stream().map(this::toListResponse).collect(Collectors.toList());
    }

    @Override
    public BlogPostResponse getPostBySlug(String slug) {
        BlogPost post = blogPostRepository.findBySlug(slug)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Blog yazısı bulunamadı: " + slug)));
        return toResponse(post);
    }

    @Override
    public List<BlogPostListResponse> getFeaturedPosts() {
        return blogPostRepository.findByIsFeaturedTrueAndStatus(BlogStatus.PUBLISHED)
                .stream().map(this::toListResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void incrementViewCount(Long id) {
        blogPostRepository.incrementViewCount(id);
    }

    @Override
    public List<String> getAllCategories() {
        return Arrays.asList("Tümü", "Rehber", "Strateji", "Teknik", "SEO", "Tasarım");
    }

    @Override
    public List<BlogPostResponse> getAllPosts() {
        return blogPostRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public BlogPostResponse getPostById(Long id) {
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Blog yazısı bulunamadı: " + id)));
        return toResponse(post);
    }

    @Override
    @Transactional
    public BlogPostResponse createPost(BlogPostRequest request) {
        if (blogPostRepository.existsBySlug(request.getSlug())) {
            throw new BaseException(
                    new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Bu slug zaten kullanılıyor: " + request.getSlug()));
        }

        // Görsel yükle
        String imageUrl = null;
        if (request.getImageUrl() != null && !request.getImageUrl().isEmpty()) {
            try {
                String base64Image = Base64.getEncoder()
                        .encodeToString(request.getImageUrl().getBytes());
                imageUrl = cloudinaryService.uploadSignature(
                        "data:image/png;base64," + base64Image,
                        "blog-" + request.getSlug()
                );
            } catch (Exception e) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Görsel yüklenemedi: " + e.getMessage()));
            }
        }

        BlogPost post = BlogPost.builder()
                .slug(request.getSlug())
                .title(request.getTitle())
                .excerpt(request.getExcerpt())
                .content(request.getContent())
                .category(request.getCategory())
                .author(request.getAuthor())
                .readTime(request.getReadTime())
                .publishedDate(request.getPublishedDate())
                .isFeatured(request.getIsFeatured())
                .viewCount(0)
                .metaTitle(request.getMetaTitle())
                .metaDescription(request.getMetaDescription())
                .status(request.getStatus())
                .imageUrl(imageUrl)
                .build();

        return toResponse(blogPostRepository.save(post));
    }

    @Override
    @Transactional
    public BlogPostResponse updatePost(Long id, BlogPostRequest request) {
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Blog yazısı bulunamadı: " + id)));

        if (!post.getSlug().equals(request.getSlug()) && blogPostRepository.existsBySlug(request.getSlug())) {
            throw new BaseException(
                    new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Bu slug zaten kullanılıyor: " + request.getSlug()));
        }

        // Yeni görsel geldiyse eskiyi sil, yenisini yükle
        if (request.getImageUrl() != null && !request.getImageUrl().isEmpty()) {
            if (post.getImageUrl() != null) {
                cloudinaryService.deleteByUrl(post.getImageUrl());
            }
            try {
                String base64Image = Base64.getEncoder()
                        .encodeToString(request.getImageUrl().getBytes());
                String newImageUrl = cloudinaryService.uploadSignature(
                        "data:image/png;base64," + base64Image,
                        "blog-" + request.getSlug()
                );
                post.setImageUrl(newImageUrl);
            } catch (Exception e) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Görsel yüklenemedi: " + e.getMessage()));
            }
        }
        // Görsel gelmemişse mevcut imageUrl korunur, dokunulmaz

        post.setSlug(request.getSlug());
        post.setTitle(request.getTitle());
        post.setExcerpt(request.getExcerpt());
        post.setContent(request.getContent());
        post.setCategory(request.getCategory());
        post.setAuthor(request.getAuthor());
        post.setReadTime(request.getReadTime());
        post.setPublishedDate(request.getPublishedDate());
        post.setIsFeatured(request.getIsFeatured());
        post.setMetaTitle(request.getMetaTitle());
        post.setMetaDescription(request.getMetaDescription());
        post.setStatus(request.getStatus());

        return toResponse(blogPostRepository.save(post));
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Blog yazısı bulunamadı: " + id)));
        // Cloudinary'den görseli sil
        if (post.getImageUrl() != null) {
            cloudinaryService.deleteByUrl(post.getImageUrl());
        }
        blogPostRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BlogPostResponse updateStatus(Long id, String status) {
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Blog yazısı bulunamadı: " + id)));
        try {
            post.setStatus(BlogStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new BaseException(
                    new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Geçersiz status: " + status));
        }
        return toResponse(blogPostRepository.save(post));
    }

    @Override
    @Transactional
    public BlogPostResponse toggleFeatured(Long id) {
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Blog yazısı bulunamadı: " + id)));
        post.setIsFeatured(!post.getIsFeatured());
        return toResponse(blogPostRepository.save(post));
    }

    private BlogPostResponse toResponse(BlogPost post) {
        return BlogPostResponse.builder()
                .id(post.getId())
                .slug(post.getSlug())
                .title(post.getTitle())
                .excerpt(post.getExcerpt())
                .content(post.getContent())
                .category(post.getCategory())
                .author(post.getAuthor())
                .readTime(post.getReadTime())
                .publishedDate(post.getPublishedDate())
                .isFeatured(post.getIsFeatured())
                .viewCount(post.getViewCount())
                .metaTitle(post.getMetaTitle())
                .metaDescription(post.getMetaDescription())
                .status(post.getStatus())
                .imageUrl(post.getImageUrl())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    private BlogPostListResponse toListResponse(BlogPost post) {
        return BlogPostListResponse.builder()
                .id(post.getId())
                .slug(post.getSlug())
                .title(post.getTitle())
                .excerpt(post.getExcerpt())
                .category(post.getCategory())
                .author(post.getAuthor())
                .readTime(post.getReadTime())
                .publishedDate(post.getPublishedDate())
                .isFeatured(post.getIsFeatured())
                .viewCount(post.getViewCount())
                .imageUrl(post.getImageUrl())
                .build();
    }
}