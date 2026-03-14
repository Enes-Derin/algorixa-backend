// MaintenanceControllerImpl.java
package com.enesderin.softvra_backend.controller.impl;

import com.enesderin.softvra_backend.controller.MaintenanceController;
import com.enesderin.softvra_backend.controller.RestBaseController;
import com.enesderin.softvra_backend.controller.RootEntity;
import com.enesderin.softvra_backend.dto.request.MaintenancePlanRequest;
import com.enesderin.softvra_backend.dto.response.MaintenancePlanResponse;
import com.enesderin.softvra_backend.servis.MaintenanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MaintenanceControllerImpl extends RestBaseController implements MaintenanceController {

    private final MaintenanceService maintenanceService;

    // PUBLIC
    @GetMapping("/public/pricing/maintenance")
    @Override
    public RootEntity<List<MaintenancePlanResponse>> getActivePlans() {
        return ok(maintenanceService.getActivePlans());
    }

    @GetMapping("/public/pricing/maintenance/{planCode}")
    @Override
    public RootEntity<MaintenancePlanResponse> getPlanByCode(@PathVariable String planCode) {
        return ok(maintenanceService.getPlanByCode(planCode));
    }

    // ADMIN
    @GetMapping("/admin/pricing/maintenance")
    @Override
    public RootEntity<List<MaintenancePlanResponse>> getAllPlans() {
        return ok(maintenanceService.getAllPlans());
    }

    @GetMapping("/admin/pricing/maintenance/{id}")
    @Override
    public RootEntity<MaintenancePlanResponse> getPlanById(@PathVariable Long id) {
        return ok(maintenanceService.getPlanById(id));
    }

    @PostMapping("/admin/pricing/maintenance")
    @Override
    public RootEntity<MaintenancePlanResponse> createPlan(@Valid @RequestBody MaintenancePlanRequest request) {
        return ok(maintenanceService.createPlan(request));
    }

    @PutMapping("/admin/pricing/maintenance/{id}")
    @Override
    public RootEntity<MaintenancePlanResponse> updatePlan(@PathVariable Long id,
                                                          @Valid @RequestBody MaintenancePlanRequest request) {
        return ok(maintenanceService.updatePlan(id, request));
    }

    @DeleteMapping("/admin/pricing/maintenance/{id}")
    @Override
    public RootEntity<String> deletePlan(@PathVariable Long id) {
        maintenanceService.deletePlan(id);
        return ok("Maintenance plan deleted successfully");
    }
}