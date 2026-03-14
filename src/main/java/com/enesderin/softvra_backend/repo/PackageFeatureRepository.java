// PackageFeatureRepository.java
package com.enesderin.softvra_backend.repo;

import com.enesderin.softvra_backend.model.PackageFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageFeatureRepository extends JpaRepository<PackageFeature, Long> {

    List<PackageFeature> findByPricingPackageIdOrderByDisplayOrderAsc(Long packageId);

    void deleteByPricingPackageId(Long packageId);
}