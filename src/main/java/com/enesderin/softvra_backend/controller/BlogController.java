// BlogController.java
package com.enesderin.softvra_backend.controller;

import com.enesderin.softvra_backend.dto.request.BlogPostRequest;
import com.enesderin.softvra_backend.dto.response.BlogPostListResponse;
import com.enesderin.softvra_backend.dto.response.BlogPostResponse;

import java.util.List;

public interface BlogController {

    // Public
    RootEntity<List<BlogPostListResponse>> getAllPublishedPosts();
    RootEntity<List<BlogPostListResponse>> getPostsByCategory(String category);
    RootEntity<BlogPostResponse> getPostBySlug(String slug);
    RootEntity<List<BlogPostListResponse>> getFeaturedPosts();
    RootEntity<String> incrementViewCount(Long id);
    RootEntity<List<String>> getAllCategories();

    // Admin
    RootEntity<List<BlogPostResponse>> getAllPosts();
    RootEntity<BlogPostResponse> getPostById(Long id);
    RootEntity<BlogPostResponse> createPost(BlogPostRequest request);
    RootEntity<BlogPostResponse> updatePost(Long id, BlogPostRequest request);
    RootEntity<String> deletePost(Long id);
    RootEntity<BlogPostResponse> updateStatus(Long id, String status);
    RootEntity<BlogPostResponse> toggleFeatured(Long id);
}