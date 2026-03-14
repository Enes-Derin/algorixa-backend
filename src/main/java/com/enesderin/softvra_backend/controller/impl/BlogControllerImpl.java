// BlogControllerImpl.java
package com.enesderin.softvra_backend.controller.impl;

import com.enesderin.softvra_backend.controller.BlogController;
import com.enesderin.softvra_backend.controller.RestBaseController;
import com.enesderin.softvra_backend.controller.RootEntity;
import com.enesderin.softvra_backend.dto.request.BlogPostRequest;
import com.enesderin.softvra_backend.dto.response.BlogPostListResponse;
import com.enesderin.softvra_backend.dto.response.BlogPostResponse;
import com.enesderin.softvra_backend.servis.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogControllerImpl extends RestBaseController implements BlogController {

    private final BlogService blogService;

    // ══════════════════════════════════════════════════════════
    // PUBLIC ENDPOINTS
    // ══════════════════════════════════════════════════════════

    @GetMapping("/public/blog/posts")
    @Override
    public RootEntity<List<BlogPostListResponse>> getAllPublishedPosts() {
        return ok(blogService.getAllPublishedPosts());
    }

    @GetMapping("/public/blog/posts/category/{category}")
    @Override
    public RootEntity<List<BlogPostListResponse>> getPostsByCategory(@PathVariable String category) {
        return ok(blogService.getPostsByCategory(category));
    }

    @GetMapping("/public/blog/posts/{slug}")
    @Override
    public RootEntity<BlogPostResponse> getPostBySlug(@PathVariable String slug) {
        return ok(blogService.getPostBySlug(slug));
    }

    @GetMapping("/public/blog/featured")
    @Override
    public RootEntity<List<BlogPostListResponse>> getFeaturedPosts() {
        return ok(blogService.getFeaturedPosts());
    }

    @PostMapping("/public/blog/posts/{id}/view")
    @Override
    public RootEntity<String> incrementViewCount(@PathVariable Long id) {
        blogService.incrementViewCount(id);
        return ok("View count incremented");
    }

    @GetMapping("/public/blog/categories")
    @Override
    public RootEntity<List<String>> getAllCategories() {
        return ok(blogService.getAllCategories());
    }

    // ══════════════════════════════════════════════════════════
    // ADMIN ENDPOINTS
    // ══════════════════════════════════════════════════════════

    @GetMapping("/admin/blog/posts")
    @Override
    public RootEntity<List<BlogPostResponse>> getAllPosts() {
        return ok(blogService.getAllPosts());
    }

    @GetMapping("/admin/blog/posts/{id}")
    @Override
    public RootEntity<BlogPostResponse> getPostById(@PathVariable Long id) {
        return ok(blogService.getPostById(id));
    }

    @PostMapping(value = "/admin/blog/posts", consumes = "multipart/form-data")
    public RootEntity<BlogPostResponse> createPost(@ModelAttribute BlogPostRequest request) {
        return ok(blogService.createPost(request));
    }

    @PutMapping(value = "/admin/blog/posts/{id}", consumes = "multipart/form-data")
    public RootEntity<BlogPostResponse> updatePost(@PathVariable Long id,
                                                   @ModelAttribute BlogPostRequest request) {
        return ok(blogService.updatePost(id, request));
    }

    @DeleteMapping("/admin/blog/posts/{id}")
    @Override
    public RootEntity<String> deletePost(@PathVariable Long id) {
        blogService.deletePost(id);
        return ok("Blog post deleted successfully");
    }

    @PatchMapping("/admin/blog/posts/{id}/status")
    @Override
    public RootEntity<BlogPostResponse> updateStatus(@PathVariable Long id,
                                                     @RequestParam String status) {
        return ok(blogService.updateStatus(id, status));
    }

    @PatchMapping("/admin/blog/posts/{id}/featured")
    @Override
    public RootEntity<BlogPostResponse> toggleFeatured(@PathVariable Long id) {
        return ok(blogService.toggleFeatured(id));
    }
}