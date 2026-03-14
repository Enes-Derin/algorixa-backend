// PricingPackageResponse.java
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
public class PricingPackageResponse {

    private Long id;
    private String packageCode;
    private String iconName;
    private String badgeText;
    private String name;
    private String tagline;
    private BigDecimal originalPrice;
    private BigDecimal currentPrice;
    private String priceNote;
    private Integer discountPercentage;
    private String deliveryTime;
    private Integer revisionCount;
    private Integer supportDays;
    private Boolean isFeatured;
    private Integer displayOrder;
    private PackageStatus status;
    private List<PackageFeatureResponse> features;
    private List<PackageNoteResponse> notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}