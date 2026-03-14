// CampaignServiceImpl.java
package com.enesderin.softvra_backend.servis.impl;

import com.enesderin.softvra_backend.dto.request.CampaignRequest;
import com.enesderin.softvra_backend.dto.response.CampaignResponse;
import com.enesderin.softvra_backend.exception.BaseException;
import com.enesderin.softvra_backend.exception.ErrorMessage;
import com.enesderin.softvra_backend.exception.MessageType;
import com.enesderin.softvra_backend.model.Campaign;
import com.enesderin.softvra_backend.repo.CampaignRepository;
import com.enesderin.softvra_backend.servis.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;

    @Override
    public Optional<CampaignResponse> getActiveCampaign() {
        return campaignRepository.findActiveCampaign(LocalDateTime.now())
                .map(this::toResponse);
    }

    @Override
    public List<CampaignResponse> getAllCampaigns() {
        return campaignRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CampaignResponse getCampaignById(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Kampanya bulunamadı: " + id)
                ));
        return toResponse(campaign);
    }

    @Override
    @Transactional
    public CampaignResponse createCampaign(CampaignRequest request) {
        Campaign campaign = Campaign.builder()
                .campaignName(request.getCampaignName())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .discountType(request.getDiscountType())
                .discountValue(request.getDiscountValue())
                .isActive(request.getIsActive())
                .applicablePackages(request.getApplicablePackages())
                .promoBarTitle(request.getPromoBarTitle())
                .promoBarDescription(request.getPromoBarDescription())
                .build();

        Campaign saved = campaignRepository.save(campaign);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public CampaignResponse updateCampaign(Long id, CampaignRequest request) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Kampanya bulunamadı: " + id)
                ));

        campaign.setCampaignName(request.getCampaignName());
        campaign.setDescription(request.getDescription());
        campaign.setStartDate(request.getStartDate());
        campaign.setEndDate(request.getEndDate());
        campaign.setDiscountType(request.getDiscountType());
        campaign.setDiscountValue(request.getDiscountValue());
        campaign.setIsActive(request.getIsActive());
        campaign.setApplicablePackages(request.getApplicablePackages());
        campaign.setPromoBarTitle(request.getPromoBarTitle());
        campaign.setPromoBarDescription(request.getPromoBarDescription());

        Campaign saved = campaignRepository.save(campaign);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteCampaign(Long id) {
        if (!campaignRepository.existsById(id)) {
            throw new BaseException(
                    new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Kampanya bulunamadı: " + id)
            );
        }
        campaignRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CampaignResponse activateCampaign(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Kampanya bulunamadı: " + id)
                ));

        campaign.setIsActive(true);
        Campaign saved = campaignRepository.save(campaign);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public CampaignResponse deactivateCampaign(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Kampanya bulunamadı: " + id)
                ));

        campaign.setIsActive(false);
        Campaign saved = campaignRepository.save(campaign);
        return toResponse(saved);
    }

    // Mapper
    private CampaignResponse toResponse(Campaign campaign) {
        return CampaignResponse.builder()
                .id(campaign.getId())
                .campaignName(campaign.getCampaignName())
                .description(campaign.getDescription())
                .startDate(campaign.getStartDate())
                .endDate(campaign.getEndDate())
                .discountType(campaign.getDiscountType())
                .discountValue(campaign.getDiscountValue())
                .isActive(campaign.getIsActive())
                .applicablePackages(campaign.getApplicablePackages())
                .promoBarTitle(campaign.getPromoBarTitle())
                .promoBarDescription(campaign.getPromoBarDescription())
                .createdAt(campaign.getCreatedAt())
                .updatedAt(campaign.getUpdatedAt())
                .build();
    }
}