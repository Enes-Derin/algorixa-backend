// AdminActivityLogRepository.java
package com.enesderin.softvra_backend.repo;

import com.enesderin.softvra_backend.model.AdminActivityLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminActivityLogRepository extends JpaRepository<AdminActivityLog, Long> {

    List<AdminActivityLog> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<AdminActivityLog> findByAdminUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    List<AdminActivityLog> findByEntityTypeAndEntityIdOrderByCreatedAtDesc(String entityType, Long entityId);
}