// PackageFeatureResponse.java
package com.enesderin.softvra_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageFeatureResponse {
    private Long id;
    private String featureText;
    private Boolean isMainFeature;
    private Integer displayOrder;
}