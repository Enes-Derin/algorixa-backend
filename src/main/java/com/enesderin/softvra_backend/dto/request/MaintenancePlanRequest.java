// MaintenancePlanRequest.java
package com.enesderin.softvra_backend.dto.request;

import com.enesderin.softvra_backend.model.PackageStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenancePlanRequest {

    @NotBlank(message = "Plan kodu gereklidir")
    private String planCode;

    private String iconName;

    private String badgeText;

    @NotBlank(message = "Plan adı gereklidir")
    private String name;

    @NotNull(message = "Aylık fiyat gereklidir")
    private BigDecimal monthlyPrice;

    private String idealFor;

    private Boolean isBestSeller = false;

    private Integer displayOrder = 0;

    private PackageStatus status = PackageStatus.ACTIVE;

    private List<MaintenanceFeatureRequest> features = new ArrayList<>();
}