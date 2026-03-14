// ProjectResultRequest.java
package com.enesderin.softvra_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResultRequest {

    @NotBlank(message = "Sonuç metni gereklidir")
    private String resultText;

    private Integer displayOrder = 0;
}