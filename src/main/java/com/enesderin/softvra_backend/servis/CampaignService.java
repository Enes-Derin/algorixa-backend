// CampaignService.java
package com.enesderin.softvra_backend.servis;

import com.enesderin.softvra_backend.dto.request.CampaignRequest;
import com.enesderin.softvra_backend.dto.response.CampaignResponse;

import java.util.List;
import java.util.Optional;

public interface CampaignService {

    // Public
    Optional<CampaignResponse> getActiveCampaign();

    // Admin
    List<CampaignResponse> getAllCampaigns();
    CampaignResponse getCampaignById(Long id);
    CampaignResponse createCampaign(CampaignRequest request);
    CampaignResponse updateCampaign(Long id, CampaignRequest request);
    void deleteCampaign(Long id);
    CampaignResponse activateCampaign(Long id);
    CampaignResponse deactivateCampaign(Long id);
}