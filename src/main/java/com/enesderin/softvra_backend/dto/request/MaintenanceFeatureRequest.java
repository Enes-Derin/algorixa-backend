// MaintenanceFeatureRequest.java
package com.enesderin.softvra_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceFeatureRequest {

    @NotBlank(message = "Özellik metni gereklidir")
    private String featureText;

    private Integer displayOrder = 0;
}