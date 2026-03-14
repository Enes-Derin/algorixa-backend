// DashboardController.java
package com.enesderin.softvra_backend.controller;

import com.enesderin.softvra_backend.dto.response.ActivityLogResponse;
import com.enesderin.softvra_backend.dto.response.DashboardStatsResponse;

import java.util.List;

public interface DashboardController {

    RootEntity<DashboardStatsResponse> getDashboardStats();
    RootEntity<List<ActivityLogResponse>> getRecentActivity(Integer limit);
    RootEntity<List<ActivityLogResponse>> getActivityByUser(Long userId, Integer limit);
}