// SiteSettingRequest.java
package com.enesderin.softvra_backend.dto.request;

import com.enesderin.softvra_backend.model.SettingType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteSettingRequest {

    @NotBlank(message = "Ayar anahtarı gereklidir")
    private String settingKey;

    private String settingValue;

    private SettingType settingType = SettingType.TEXT;

    private String description;
}