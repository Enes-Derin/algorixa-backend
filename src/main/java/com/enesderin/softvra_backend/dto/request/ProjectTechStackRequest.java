// ProjectTechStackRequest.java
package com.enesderin.softvra_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTechStackRequest {

    @NotBlank(message = "Teknoloji adı gereklidir")
    private String technology;

    private Integer displayOrder = 0;
}