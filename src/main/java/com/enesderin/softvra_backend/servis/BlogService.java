// BlogService.java
package com.enesderin.softvra_backend.servis;

import com.enesderin.softvra_backend.dto.request.BlogPostRequest;
import com.enesderin.softvra_backend.dto.response.BlogPostListResponse;
import com.enesderin.softvra_backend.dto.response.BlogPostResponse;

import java.util.List;

public interface BlogService {

    // Public endpoints
    List<BlogPostListResponse> getAllPublishedPosts();
    List<BlogPostListResponse> getPostsByCategory(String category);
    BlogPostResponse getPostBySlug(String slug);
    List<BlogPostListResponse> getFeaturedPosts();
    void incrementViewCount(Long id);
    List<String> getAllCategories();

    // Admin endpoints
    List<BlogPostResponse> getAllPosts();
    BlogPostResponse getPostById(Long id);
    BlogPostResponse createPost(BlogPostRequest request);
    BlogPostResponse updatePost(Long id, BlogPostRequest request);
    void deletePost(Long id);
    BlogPostResponse updateStatus(Long id, String status);
    BlogPostResponse toggleFeatured(Long id);
}