package com.enesderin.softvra_backend.controller.impl;

import com.enesderin.softvra_backend.controller.RestBaseController;
import com.enesderin.softvra_backend.controller.RootEntity;
import com.enesderin.softvra_backend.controller.SiteSettingController;
import com.enesderin.softvra_backend.dto.request.SiteSettingRequest;
import com.enesderin.softvra_backend.dto.response.SiteSettingResponse;
import com.enesderin.softvra_backend.servis.SiteSettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/settings")
@RequiredArgsConstructor
public class SiteSettingControllerImpl extends RestBaseController implements SiteSettingController {

    private final SiteSettingService siteSettingService;

    @GetMapping
    @Override
    public RootEntity<List<SiteSettingResponse>> getAllSettings() {
        return ok(siteSettingService.getAllSettings());
    }

    // ✅ :.+ regex → nokta içeren key'leri yakala
    @GetMapping("/{key:.+}")
    @Override
    public RootEntity<SiteSettingResponse> getSettingByKey(@PathVariable("key") String key) {
        return ok(siteSettingService.getSettingByKey(key));
    }

    @PutMapping("/{key:.+}")
    @Override
    public RootEntity<SiteSettingResponse> createOrUpdateSetting(
            @PathVariable("key") String key,
            @Valid @RequestBody SiteSettingRequest request) {
        return ok(siteSettingService.createOrUpdateSetting(key, request));
    }

    @DeleteMapping("/{key:.+}")
    @Override
    public RootEntity<String> deleteSetting(@PathVariable("key") String key) {
        siteSettingService.deleteSetting(key);
        return ok("Setting deleted successfully");
    }
}