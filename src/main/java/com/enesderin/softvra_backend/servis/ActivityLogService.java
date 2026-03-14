// ActivityLogService.java
package com.enesderin.softvra_backend.servis;

import com.enesderin.softvra_backend.dto.response.ActivityLogResponse;

import java.util.List;

public interface ActivityLogService {

    void logActivity(Long userId, String actionType, String entityType, Long entityId,
                     String details, String ipAddress);

    List<ActivityLogResponse> getRecentActivity(int limit);

    List<ActivityLogResponse> getActivityByUser(Long userId, int limit);

    List<ActivityLogResponse> getActivityByEntity(String entityType, Long entityId);
}