// SiteSettingService.java
package com.enesderin.softvra_backend.servis;

import com.enesderin.softvra_backend.dto.request.SiteSettingRequest;
import com.enesderin.softvra_backend.dto.response.SiteSettingResponse;

import java.util.List;

public interface SiteSettingService {

    List<SiteSettingResponse> getAllSettings();
    SiteSettingResponse getSettingByKey(String key);
    SiteSettingResponse createOrUpdateSetting(String key, SiteSettingRequest request);
    void deleteSetting(String key);
}