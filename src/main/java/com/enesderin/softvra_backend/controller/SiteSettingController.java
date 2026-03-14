// SiteSettingController.java
package com.enesderin.softvra_backend.controller;

import com.enesderin.softvra_backend.dto.request.SiteSettingRequest;
import com.enesderin.softvra_backend.dto.response.SiteSettingResponse;

import java.util.List;

public interface SiteSettingController {

    RootEntity<List<SiteSettingResponse>> getAllSettings();
    RootEntity<SiteSettingResponse> getSettingByKey(String key);
    RootEntity<SiteSettingResponse> createOrUpdateSetting(String key, SiteSettingRequest request);
    RootEntity<String> deleteSetting(String key);
}