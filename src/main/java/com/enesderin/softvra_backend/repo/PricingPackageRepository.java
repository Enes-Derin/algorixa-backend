// PricingPackageRepository.java
package com.enesderin.softvra_backend.repo;

import com.enesderin.softvra_backend.model.PackageStatus;
import com.enesderin.softvra_backend.model.PricingPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PricingPackageRepository extends JpaRepository<PricingPackage, Long> {

    List<PricingPackage> findByStatusOrderByDisplayOrderAsc(PackageStatus status);

    Optional<PricingPackage> findByPackageCode(String packageCode);

    List<PricingPackage> findAllByOrderByDisplayOrderAsc();

    boolean existsByPackageCode(String packageCode);
}