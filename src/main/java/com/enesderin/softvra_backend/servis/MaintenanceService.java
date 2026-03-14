// MaintenanceService.java
package com.enesderin.softvra_backend.servis;

import com.enesderin.softvra_backend.dto.request.MaintenancePlanRequest;
import com.enesderin.softvra_backend.dto.response.MaintenancePlanResponse;

import java.util.List;

public interface MaintenanceService {

    // Public
    List<MaintenancePlanResponse> getActivePlans();
    MaintenancePlanResponse getPlanByCode(String planCode);

    // Admin
    List<MaintenancePlanResponse> getAllPlans();
    MaintenancePlanResponse getPlanById(Long id);
    MaintenancePlanResponse createPlan(MaintenancePlanRequest request);
    MaintenancePlanResponse updatePlan(Long id, MaintenancePlanRequest request);
    void deletePlan(Long id);
}