// CampaignControllerImpl.java
package com.enesderin.softvra_backend.controller.impl;

import com.enesderin.softvra_backend.controller.CampaignController;
import com.enesderin.softvra_backend.controller.RestBaseController;
import com.enesderin.softvra_backend.controller.RootEntity;
import com.enesderin.softvra_backend.dto.request.CampaignRequest;
import com.enesderin.softvra_backend.dto.response.CampaignResponse;
import com.enesderin.softvra_backend.servis.CampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CampaignControllerImpl extends RestBaseController implements CampaignController {

    private final CampaignService campaignService;

    // PUBLIC
    @GetMapping("/public/pricing/campaign/active")
    @Override
    public RootEntity<CampaignResponse> getActiveCampaign() {
        return campaignService.getActiveCampaign()
                .map(this::ok)
                .orElse(ok(null));
    }

    // ADMIN
    @GetMapping("/admin/campaigns")
    @Override
    public RootEntity<List<CampaignResponse>> getAllCampaigns() {
        return ok(campaignService.getAllCampaigns());
    }

    @GetMapping("/admin/campaigns/{id}")
    @Override
    public RootEntity<CampaignResponse> getCampaignById(@PathVariable Long id) {
        return ok(campaignService.getCampaignById(id));
    }

    @PostMapping("/admin/campaigns")
    @Override
    public RootEntity<CampaignResponse> createCampaign(@Valid @RequestBody CampaignRequest request) {
        return ok(campaignService.createCampaign(request));
    }

    @PutMapping("/admin/campaigns/{id}")
    @Override
    public RootEntity<CampaignResponse> updateCampaign(@PathVariable Long id,
                                                       @Valid @RequestBody CampaignRequest request) {
        return ok(campaignService.updateCampaign(id, request));
    }

    @DeleteMapping("/admin/campaigns/{id}")
    @Override
    public RootEntity<String> deleteCampaign(@PathVariable Long id) {
        campaignService.deleteCampaign(id);
        return ok("Campaign deleted successfully");
    }

    @PatchMapping("/admin/campaigns/{id}/activate")
    @Override
    public RootEntity<CampaignResponse> activateCampaign(@PathVariable Long id) {
        return ok(campaignService.activateCampaign(id));
    }

    @PatchMapping("/admin/campaigns/{id}/deactivate")
    @Override
    public RootEntity<CampaignResponse> deactivateCampaign(@PathVariable Long id) {
        return ok(campaignService.deactivateCampaign(id));
    }
}