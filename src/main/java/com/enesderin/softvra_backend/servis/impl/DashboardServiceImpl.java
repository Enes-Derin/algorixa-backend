// DashboardServiceImpl.java
package com.enesderin.softvra_backend.servis.impl;

import com.enesderin.softvra_backend.dto.response.ActivityLogResponse;
import com.enesderin.softvra_backend.dto.response.BlogPostListResponse;
import com.enesderin.softvra_backend.dto.response.DashboardStatsResponse;
import com.enesderin.softvra_backend.model.BlogStatus;
import com.enesderin.softvra_backend.model.ProjectStatus;
import com.enesderin.softvra_backend.repo.*;
import com.enesderin.softvra_backend.servis.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final BlogPostRepository blogPostRepository;
    private final PortfolioProjectRepository portfolioProjectRepository;
    private final ContactMessageRepository contactMessageRepository;
    private final CampaignRepository campaignRepository;
    private final AdminActivityLogRepository activityLogRepository;

    @Override
    public DashboardStatsResponse getDashboardStats() {
        // Blog stats
        Long totalBlogPosts = blogPostRepository.count();
        Long publishedPosts = blogPostRepository.countByStatus(BlogStatus.PUBLISHED);
        Long draftPosts = blogPostRepository.countByStatus(BlogStatus.DRAFT);

        // Project stats
        Long totalProjects = portfolioProjectRepository.count();
        Long publishedProjects = portfolioProjectRepository.countByStatus(ProjectStatus.PUBLISHED);
        Long featuredProjects = portfolioProjectRepository.findByIsFeaturedTrueAndStatus(ProjectStatus.PUBLISHED)
                .map(p -> 1L)
                .orElse(0L);

        // Contact stats
        Long totalContacts = contactMessageRepository.count();
        Long unreadContacts = contactMessageRepository.countByReadFalse();

        // Campaign stats
        Long activeCampaigns = campaignRepository.findActiveCampaign(LocalDateTime.now())
                .map(c -> 1L)
                .orElse(0L);

        // Recent activity (son 10)
        List<ActivityLogResponse> recentActivity = activityLogRepository
                .findAllByOrderByCreatedAtDesc(PageRequest.of(0, 10))
                .stream()
                .map(log -> ActivityLogResponse.builder()
                        .id(log.getId())
                        .username(log.getAdminUser() != null ? log.getAdminUser().getUsername() : "System")
                        .actionType(log.getActionType())
                        .entityType(log.getEntityType())
                        .entityId(log.getEntityId())
                        .details(log.getDetails())
                        .ipAddress(log.getIpAddress())
                        .createdAt(log.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        // Popular blog posts (en çok görüntülenen 5)
        List<BlogPostListResponse> popularPosts = blogPostRepository
                .findByStatusOrderByPublishedDateDesc(BlogStatus.PUBLISHED)
                .stream()
                .sorted((p1, p2) -> Integer.compare(p2.getViewCount(), p1.getViewCount()))
                .limit(5)
                .map(post -> BlogPostListResponse.builder()
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
                        .build())
                .collect(Collectors.toList());

        return DashboardStatsResponse.builder()
                .totalBlogPosts(totalBlogPosts)
                .publishedPosts(publishedPosts)
                .draftPosts(draftPosts)
                .totalProjects(totalProjects)
                .publishedProjects(publishedProjects)
                .featuredProjects(featuredProjects)
                .newContactSubmissions(totalContacts)
                .unreadContacts(unreadContacts)
                .activeCampaigns(activeCampaigns)
                .recentActivity(recentActivity)
                .popularBlogPosts(popularPosts)
                .build();
    }
}