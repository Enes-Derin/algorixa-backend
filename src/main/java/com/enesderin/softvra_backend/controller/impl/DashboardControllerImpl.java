// DashboardControllerImpl.java
package com.enesderin.softvra_backend.controller.impl;

import com.enesderin.softvra_backend.controller.DashboardController;
import com.enesderin.softvra_backend.controller.RestBaseController;
import com.enesderin.softvra_backend.controller.RootEntity;
import com.enesderin.softvra_backend.dto.response.ActivityLogResponse;
import com.enesderin.softvra_backend.dto.response.DashboardStatsResponse;
import com.enesderin.softvra_backend.servis.ActivityLogService;
import com.enesderin.softvra_backend.servis.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class DashboardControllerImpl extends RestBaseController implements DashboardController {

    private final DashboardService dashboardService;
    private final ActivityLogService activityLogService;

    @GetMapping("/stats")
    @Override
    public RootEntity<DashboardStatsResponse> getDashboardStats() {
        return ok(dashboardService.getDashboardStats());
    }

    @GetMapping("/activity/recent")
    @Override
    public RootEntity<List<ActivityLogResponse>> getRecentActivity(
            @RequestParam(defaultValue = "20") Integer limit) {
        return ok(activityLogService.getRecentActivity(limit));
    }

    @GetMapping("/activity/user/{userId}")
    @Override
    public RootEntity<List<ActivityLogResponse>> getActivityByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "20") Integer limit) {
        return ok(activityLogService.getActivityByUser(userId, limit));
    }
}