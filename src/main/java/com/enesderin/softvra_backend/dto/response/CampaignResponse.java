// CampaignResponse.java
package com.enesderin.softvra_backend.dto.response;

import com.enesderin.softvra_backend.model.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignResponse {

    private Long id;
    private String campaignName;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private Boolean isActive;
    private String applicablePackages;
    private String promoBarTitle;
    private String promoBarDescription;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}