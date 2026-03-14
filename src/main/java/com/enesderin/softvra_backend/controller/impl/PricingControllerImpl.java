// PricingControllerImpl.java
package com.enesderin.softvra_backend.controller.impl;

import com.enesderin.softvra_backend.controller.PricingController;
import com.enesderin.softvra_backend.controller.RestBaseController;
import com.enesderin.softvra_backend.controller.RootEntity;
import com.enesderin.softvra_backend.dto.request.PricingPackageRequest;
import com.enesderin.softvra_backend.dto.response.PricingPackageResponse;
import com.enesderin.softvra_backend.servis.PricingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PricingControllerImpl extends RestBaseController implements PricingController {

    private final PricingService pricingService;

    // PUBLIC
    @GetMapping("/public/pricing/packages")
    @Override
    public RootEntity<List<PricingPackageResponse>> getActivePackages() {
        return ok(pricingService.getActivePackages());
    }

    @GetMapping("/public/pricing/packages/{packageCode}")
    @Override
    public RootEntity<PricingPackageResponse> getPackageByCode(@PathVariable String packageCode) {
        return ok(pricingService.getPackageByCode(packageCode));
    }

    // ADMIN
    @GetMapping("/admin/pricing/packages")
    @Override
    public RootEntity<List<PricingPackageResponse>> getAllPackages() {
        return ok(pricingService.getAllPackages());
    }

    @GetMapping("/admin/pricing/packages/{id}")
    @Override
    public RootEntity<PricingPackageResponse> getPackageById(@PathVariable Long id) {
        return ok(pricingService.getPackageById(id));
    }

    @PostMapping("/admin/pricing/packages")
    @Override
    public RootEntity<PricingPackageResponse> createPackage(@Valid @RequestBody PricingPackageRequest request) {
        return ok(pricingService.createPackage(request));
    }

    @PutMapping("/admin/pricing/packages/{id}")
    @Override
    public RootEntity<PricingPackageResponse> updatePackage(@PathVariable Long id,
                                                            @Valid @RequestBody PricingPackageRequest request) {
        return ok(pricingService.updatePackage(id, request));
    }

    @DeleteMapping("/admin/pricing/packages/{id}")
    @Override
    public RootEntity<String> deletePackage(@PathVariable Long id) {
        pricingService.deletePackage(id);
        return ok("Package deleted successfully");
    }

    @PatchMapping("/admin/pricing/packages/{id}/order")
    @Override
    public RootEntity<PricingPackageResponse> updateDisplayOrder(@PathVariable Long id,
                                                                 @RequestParam Integer order) {
        return ok(pricingService.updateDisplayOrder(id, order));
    }
}