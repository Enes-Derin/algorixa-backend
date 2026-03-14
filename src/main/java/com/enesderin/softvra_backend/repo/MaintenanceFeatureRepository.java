// MaintenanceFeatureRepository.java
package com.enesderin.softvra_backend.repo;

import com.enesderin.softvra_backend.model.MaintenanceFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceFeatureRepository extends JpaRepository<MaintenanceFeature, Long> {

    List<MaintenanceFeature> findByMaintenancePlanIdOrderByDisplayOrderAsc(Long planId);

    void deleteByMaintenancePlanId(Long planId);
}