// MaintenanceServiceImpl.java
package com.enesderin.softvra_backend.servis.impl;

import com.enesderin.softvra_backend.dto.request.MaintenancePlanRequest;
import com.enesderin.softvra_backend.dto.response.MaintenanceFeatureResponse;
import com.enesderin.softvra_backend.dto.response.MaintenancePlanResponse;
import com.enesderin.softvra_backend.exception.BaseException;
import com.enesderin.softvra_backend.exception.ErrorMessage;
import com.enesderin.softvra_backend.exception.MessageType;
import com.enesderin.softvra_backend.model.MaintenanceFeature;
import com.enesderin.softvra_backend.model.MaintenancePlan;
import com.enesderin.softvra_backend.model.PackageStatus;
import com.enesderin.softvra_backend.repo.MaintenanceFeatureRepository;
import com.enesderin.softvra_backend.repo.MaintenancePlanRepository;
import com.enesderin.softvra_backend.servis.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenancePlanRepository planRepository;
    private final MaintenanceFeatureRepository featureRepository;

    @Override
    public List<MaintenancePlanResponse> getActivePlans() {
        return planRepository.findByStatusOrderByDisplayOrderAsc(PackageStatus.ACTIVE)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MaintenancePlanResponse getPlanByCode(String planCode) {
        MaintenancePlan plan = planRepository.findByPlanCode(planCode)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Bakım planı bulunamadı: " + planCode)
                ));
        return toResponse(plan);
    }

    @Override
    public List<MaintenancePlanResponse> getAllPlans() {
        return planRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MaintenancePlanResponse getPlanById(Long id) {
        MaintenancePlan plan = planRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Bakım planı bulunamadı: " + id)
                ));
        return toResponse(plan);
    }

    @Override
    @Transactional
    public MaintenancePlanResponse createPlan(MaintenancePlanRequest request) {
        if (planRepository.existsByPlanCode(request.getPlanCode())) {
            throw new BaseException(
                    new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Bu plan kodu zaten kullanılıyor: " + request.getPlanCode())
            );
        }

        MaintenancePlan plan = MaintenancePlan.builder()
                .planCode(request.getPlanCode())
                .iconName(request.getIconName())
                .badgeText(request.getBadgeText())
                .name(request.getName())
                .monthlyPrice(request.getMonthlyPrice())
                .idealFor(request.getIdealFor())
                .isBestSeller(request.getIsBestSeller())
                .displayOrder(request.getDisplayOrder())
                .status(request.getStatus())
                .build();

        MaintenancePlan savedPlan = planRepository.save(plan);

        // Features ekle
        if (request.getFeatures() != null) {
            request.getFeatures().forEach(featReq -> {
                MaintenanceFeature feature = MaintenanceFeature.builder()
                        .maintenancePlan(savedPlan)
                        .featureText(featReq.getFeatureText())
                        .displayOrder(featReq.getDisplayOrder())
                        .build();
                savedPlan.addFeature(feature);
            });
        }

        MaintenancePlan finalPlan = planRepository.save(savedPlan);
        return toResponse(finalPlan);
    }

    @Override
    @Transactional
    public MaintenancePlanResponse updatePlan(Long id, MaintenancePlanRequest request) {
        MaintenancePlan plan = planRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Bakım planı bulunamadı: " + id)
                ));

        if (!plan.getPlanCode().equals(request.getPlanCode())
                && planRepository.existsByPlanCode(request.getPlanCode())) {
            throw new BaseException(
                    new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Bu plan kodu zaten kullanılıyor: " + request.getPlanCode())
            );
        }

        plan.setPlanCode(request.getPlanCode());
        plan.setIconName(request.getIconName());
        plan.setBadgeText(request.getBadgeText());
        plan.setName(request.getName());
        plan.setMonthlyPrice(request.getMonthlyPrice());
        plan.setIdealFor(request.getIdealFor());
        plan.setIsBestSeller(request.getIsBestSeller());
        plan.setDisplayOrder(request.getDisplayOrder());
        plan.setStatus(request.getStatus());

        // Eski features sil
        featureRepository.deleteByMaintenancePlanId(id);
        plan.getFeatures().clear();

        // Yeni features ekle
        if (request.getFeatures() != null) {
            request.getFeatures().forEach(featReq -> {
                MaintenanceFeature feature = MaintenanceFeature.builder()
                        .maintenancePlan(plan)
                        .featureText(featReq.getFeatureText())
                        .displayOrder(featReq.getDisplayOrder())
                        .build();
                plan.addFeature(feature);
            });
        }

        MaintenancePlan saved = planRepository.save(plan);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void deletePlan(Long id) {
        if (!planRepository.existsById(id)) {
            throw new BaseException(
                    new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Bakım planı bulunamadı: " + id)
            );
        }
        planRepository.deleteById(id);
    }

    // Mapper
    private MaintenancePlanResponse toResponse(MaintenancePlan plan) {
        return MaintenancePlanResponse.builder()
                .id(plan.getId())
                .planCode(plan.getPlanCode())
                .iconName(plan.getIconName())
                .badgeText(plan.getBadgeText())
                .name(plan.getName())
                .monthlyPrice(plan.getMonthlyPrice())
                .idealFor(plan.getIdealFor())
                .isBestSeller(plan.getIsBestSeller())
                .displayOrder(plan.getDisplayOrder())
                .status(plan.getStatus())
                .features(plan.getFeatures() != null ? plan.getFeatures().stream()
                        .map(f -> MaintenanceFeatureResponse.builder()
                                .id(f.getId())
                                .featureText(f.getFeatureText())
                                .displayOrder(f.getDisplayOrder())
                                .build())
                        .collect(Collectors.toList()) : new ArrayList<>())
                .createdAt(plan.getCreatedAt())
                .updatedAt(plan.getUpdatedAt())
                .build();
    }
}