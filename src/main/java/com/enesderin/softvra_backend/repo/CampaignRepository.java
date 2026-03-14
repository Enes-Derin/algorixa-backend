// CampaignRepository.java
package com.enesderin.softvra_backend.repo;

import com.enesderin.softvra_backend.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    @Query("SELECT c FROM Campaign c WHERE c.isActive = true AND c.startDate <= :now AND c.endDate >= :now")
    Optional<Campaign> findActiveCampaign(LocalDateTime now);

    List<Campaign> findByIsActiveTrueOrderByCreatedAtDesc();

    List<Campaign> findAllByOrderByCreatedAtDesc();
}