// DashboardStatsResponse.java
package com.enesderin.softvra_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardStatsResponse {

    private Long totalBlogPosts;
    private Long publishedPosts;
    private Long draftPosts;

    private Long totalProjects;
    private Long publishedProjects;
    private Long featuredProjects;

    private Long newContactSubmissions;
    private Long unreadContacts;

    private Long activeCampaigns;

    private List<ActivityLogResponse> recentActivity;

    private List<BlogPostListResponse> popularBlogPosts;
}