// SiteSettingServiceImpl.java
package com.enesderin.softvra_backend.servis.impl;

import com.enesderin.softvra_backend.dto.request.SiteSettingRequest;
import com.enesderin.softvra_backend.dto.response.SiteSettingResponse;
import com.enesderin.softvra_backend.exception.BaseException;
import com.enesderin.softvra_backend.exception.ErrorMessage;
import com.enesderin.softvra_backend.exception.MessageType;
import com.enesderin.softvra_backend.model.SiteSetting;
import com.enesderin.softvra_backend.repo.SiteSettingRepository;
import com.enesderin.softvra_backend.servis.SiteSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SiteSettingServiceImpl implements SiteSettingService {

    private final SiteSettingRepository settingRepository;

    @Override
    public List<SiteSettingResponse> getAllSettings() {
        return settingRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SiteSettingResponse getSettingByKey(String key) {
        SiteSetting setting = settingRepository.findBySettingKey(key)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Ayar bulunamadı: " + key)
                ));
        return toResponse(setting);
    }

    @Override
    @Transactional
    public SiteSettingResponse createOrUpdateSetting(String key, SiteSettingRequest request) {
        SiteSetting setting = settingRepository.findBySettingKey(key)
                .orElse(SiteSetting.builder()
                        .settingKey(key)
                        .build());

        setting.setSettingValue(request.getSettingValue());
        setting.setSettingType(request.getSettingType());
        setting.setDescription(request.getDescription());

        SiteSetting saved = settingRepository.save(setting);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteSetting(String key) {
        SiteSetting setting = settingRepository.findBySettingKey(key)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Ayar bulunamadı: " + key)
                ));
        settingRepository.delete(setting);
    }

    // Mapper
    private SiteSettingResponse toResponse(SiteSetting setting) {
        return SiteSettingResponse.builder()
                .id(setting.getId())
                .settingKey(setting.getSettingKey())
                .settingValue(setting.getSettingValue())
                .settingType(setting.getSettingType())
                .description(setting.getDescription())
                .updatedAt(setting.getUpdatedAt())
                .build();
    }
}