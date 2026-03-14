// CampaignController.java
package com.enesderin.softvra_backend.controller;

import com.enesderin.softvra_backend.dto.request.CampaignRequest;
import com.enesderin.softvra_backend.dto.response.CampaignResponse;

import java.util.List;

public interface CampaignController {

    // Public
    RootEntity<CampaignResponse> getActiveCampaign();

    // Admin
    RootEntity<List<CampaignResponse>> getAllCampaigns();
    RootEntity<CampaignResponse> getCampaignById(Long id);
    RootEntity<CampaignResponse> createCampaign(CampaignRequest request);
    RootEntity<CampaignResponse> updateCampaign(Long id, CampaignRequest request);
    RootEntity<String> deleteCampaign(Long id);
    RootEntity<CampaignResponse> activateCampaign(Long id);
    RootEntity<CampaignResponse> deactivateCampaign(Long id);
}