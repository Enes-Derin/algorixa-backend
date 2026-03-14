// PricingService.java
package com.enesderin.softvra_backend.servis;

import com.enesderin.softvra_backend.dto.request.PricingPackageRequest;
import com.enesderin.softvra_backend.dto.response.PricingPackageResponse;

import java.util.List;

public interface PricingService {

    // Public endpoints
    List<PricingPackageResponse> getActivePackages();
    PricingPackageResponse getPackageByCode(String packageCode);

    // Admin endpoints
    List<PricingPackageResponse> getAllPackages();
    PricingPackageResponse getPackageById(Long id);
    PricingPackageResponse createPackage(PricingPackageRequest request);
    PricingPackageResponse updatePackage(Long id, PricingPackageRequest request);
    void deletePackage(Long id);
    PricingPackageResponse updateDisplayOrder(Long id, Integer order);
}