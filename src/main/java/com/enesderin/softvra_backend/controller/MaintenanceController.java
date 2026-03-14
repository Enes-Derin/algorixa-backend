// MaintenanceController.java
package com.enesderin.softvra_backend.controller;

import com.enesderin.softvra_backend.dto.request.MaintenancePlanRequest;
import com.enesderin.softvra_backend.dto.response.MaintenancePlanResponse;

import java.util.List;

public interface MaintenanceController {

    // Public
    RootEntity<List<MaintenancePlanResponse>> getActivePlans();
    RootEntity<MaintenancePlanResponse> getPlanByCode(String planCode);

    // Admin
    RootEntity<List<MaintenancePlanResponse>> getAllPlans();
    RootEntity<MaintenancePlanResponse> getPlanById(Long id);
    RootEntity<MaintenancePlanResponse> createPlan(MaintenancePlanRequest request);
    RootEntity<MaintenancePlanResponse> updatePlan(Long id, MaintenancePlanRequest request);
    RootEntity<String> deletePlan(Long id);
}