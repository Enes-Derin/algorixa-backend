// PackageFeatureRequest.java
package com.enesderin.softvra_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageFeatureRequest {

    @NotBlank(message = "Özellik metni gereklidir")
    private String featureText;

    private Boolean isMainFeature = true;

    private Integer displayOrder = 0;
}