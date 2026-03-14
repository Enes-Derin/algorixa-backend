// CampaignRequest.java
package com.enesderin.softvra_backend.dto.request;

import com.enesderin.softvra_backend.model.DiscountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignRequest {

    @NotBlank(message = "Kampanya adı gereklidir")
    private String campaignName;

    private String description;

    @NotNull(message = "Başlangıç tarihi gereklidir")
    private LocalDateTime startDate;

    @NotNull(message = "Bitiş tarihi gereklidir")
    private LocalDateTime endDate;

    @NotNull(message = "İndirim tipi gereklidir")
    private DiscountType discountType;

    @NotNull(message = "İndirim değeri gereklidir")
    private BigDecimal discountValue;

    private Boolean isActive = true;

    private String applicablePackages; // JSON string

    private String promoBarTitle;

    private String promoBarDescription;
}