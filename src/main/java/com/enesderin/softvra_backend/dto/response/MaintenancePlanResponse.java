// MaintenancePlanResponse.java
package com.enesderin.softvra_backend.dto.response;

import com.enesderin.softvra_backend.model.PackageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaintenancePlanResponse {

    private Long id;
    private String planCode;
    private String iconName;
    private String badgeText;
    private String name;
    private BigDecimal monthlyPrice;
    private String idealFor;
    private Boolean isBestSeller;
    private Integer displayOrder;
    private PackageStatus status;
    private List<MaintenanceFeatureResponse> features;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}