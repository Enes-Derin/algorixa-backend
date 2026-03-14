// MaintenancePlanRepository.java
package com.enesderin.softvra_backend.repo;

import com.enesderin.softvra_backend.model.MaintenancePlan;
import com.enesderin.softvra_backend.model.PackageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaintenancePlanRepository extends JpaRepository<MaintenancePlan, Long> {

    List<MaintenancePlan> findByStatusOrderByDisplayOrderAsc(PackageStatus status);

    Optional<MaintenancePlan> findByPlanCode(String planCode);

    List<MaintenancePlan> findAllByOrderByDisplayOrderAsc();

    boolean existsByPlanCode(String planCode);
}