// ProjectTechStackResponse.java
package com.enesderin.softvra_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectTechStackResponse {
    private Long id;
    private String technology;
    private Integer displayOrder;
}