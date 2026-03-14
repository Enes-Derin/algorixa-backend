// PricingController.java
package com.enesderin.softvra_backend.controller;

import com.enesderin.softvra_backend.dto.request.PricingPackageRequest;
import com.enesderin.softvra_backend.dto.response.PricingPackageResponse;

import java.util.List;

public interface PricingController {

    // Public
    RootEntity<List<PricingPackageResponse>> getActivePackages();
    RootEntity<PricingPackageResponse> getPackageByCode(String packageCode);

    // Admin
    RootEntity<List<PricingPackageResponse>> getAllPackages();
    RootEntity<PricingPackageResponse> getPackageById(Long id);
    RootEntity<PricingPackageResponse> createPackage(PricingPackageRequest request);
    RootEntity<PricingPackageResponse> updatePackage(Long id, PricingPackageRequest request);
    RootEntity<String> deletePackage(Long id);
    RootEntity<PricingPackageResponse> updateDisplayOrder(Long id, Integer order);
}