// SiteSettingResponse.java
package com.enesderin.softvra_backend.dto.response;

import com.enesderin.softvra_backend.model.SettingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SiteSettingResponse {
    private Long id;
    private String settingKey;
    private String settingValue;
    private SettingType settingType;
    private String description;
    private LocalDateTime updatedAt;
}