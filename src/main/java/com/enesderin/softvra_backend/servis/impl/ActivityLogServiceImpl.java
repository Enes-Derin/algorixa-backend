// ActivityLogServiceImpl.java
package com.enesderin.softvra_backend.servis.impl;

import com.enesderin.softvra_backend.dto.response.ActivityLogResponse;
import com.enesderin.softvra_backend.model.AdminActivityLog;
import com.enesderin.softvra_backend.model.User;
import com.enesderin.softvra_backend.repo.AdminActivityLogRepository;
import com.enesderin.softvra_backend.repo.UserRepository;
import com.enesderin.softvra_backend.servis.ActivityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityLogServiceImpl implements ActivityLogService {

    private final AdminActivityLogRepository activityLogRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void logActivity(Long userId, String actionType, String entityType,
                            Long entityId, String details, String ipAddress) {
        User user = userId != null ? userRepository.findById(userId).orElse(null) : null;

        AdminActivityLog log = AdminActivityLog.builder()
                .adminUser(user)
                .actionType(actionType)
                .entityType(entityType)
                .entityId(entityId)
                .details(details)
                .ipAddress(ipAddress)
                .build();

        activityLogRepository.save(log);
    }

    @Override
    public List<ActivityLogResponse> getRecentActivity(int limit) {
        return activityLogRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, limit))
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ActivityLogResponse> getActivityByUser(Long userId, int limit) {
        return activityLogRepository.findByAdminUserIdOrderByCreatedAtDesc(userId, PageRequest.of(0, limit))
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ActivityLogResponse> getActivityByEntity(String entityType, Long entityId) {
        return activityLogRepository.findByEntityTypeAndEntityIdOrderByCreatedAtDesc(entityType, entityId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Mapper
    private ActivityLogResponse toResponse(AdminActivityLog log) {
        return ActivityLogResponse.builder()
                .id(log.getId())
                .username(log.getAdminUser() != null ? log.getAdminUser().getUsername() : "System")
                .actionType(log.getActionType())
                .entityType(log.getEntityType())
                .entityId(log.getEntityId())
                .details(log.getDetails())
                .ipAddress(log.getIpAddress())
                .createdAt(log.getCreatedAt())
                .build();
    }
}