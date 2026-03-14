// PricingPackageRequest.java
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
public class PricingPackageRequest {

    @NotBlank(message = "Paket kodu gereklidir")
    private String packageCode;

    private String iconName;

    private String badgeText;

    @NotBlank(message = "Paket adı gereklidir")
    private String name;

    private String tagline;

    private BigDecimal originalPrice;

    @NotNull(message = "Güncel fiyat gereklidir")
    private BigDecimal currentPrice;

    private String priceNote;

    private Integer discountPercentage;

    private String deliveryTime;

    private Integer revisionCount;

    private Integer supportDays;

    private Boolean isFeatured = false;

    private Integer displayOrder = 0;

    private PackageStatus status = PackageStatus.ACTIVE;

    private List<PackageFeatureRequest> features = new ArrayList<>();

    private List<PackageNoteRequest> notes = new ArrayList<>();
}