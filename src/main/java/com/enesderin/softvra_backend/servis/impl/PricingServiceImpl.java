// PricingServiceImpl.java
package com.enesderin.softvra_backend.servis.impl;

import com.enesderin.softvra_backend.dto.request.PricingPackageRequest;
import com.enesderin.softvra_backend.dto.response.PackageFeatureResponse;
import com.enesderin.softvra_backend.dto.response.PackageNoteResponse;
import com.enesderin.softvra_backend.dto.response.PricingPackageResponse;
import com.enesderin.softvra_backend.exception.BaseException;
import com.enesderin.softvra_backend.exception.ErrorMessage;
import com.enesderin.softvra_backend.exception.MessageType;
import com.enesderin.softvra_backend.model.*;
import com.enesderin.softvra_backend.repo.PackageFeatureRepository;
import com.enesderin.softvra_backend.repo.PackageNoteRepository;
import com.enesderin.softvra_backend.repo.PricingPackageRepository;
import com.enesderin.softvra_backend.servis.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {

    private final PricingPackageRepository packageRepository;
    private final PackageFeatureRepository featureRepository;
    private final PackageNoteRepository noteRepository;

    @Override
    public List<PricingPackageResponse> getActivePackages() {
        return packageRepository.findByStatusOrderByDisplayOrderAsc(PackageStatus.ACTIVE)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PricingPackageResponse getPackageByCode(String packageCode) {
        PricingPackage pkg = packageRepository.findByPackageCode(packageCode)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Paket bulunamadı: " + packageCode)
                ));
        return toResponse(pkg);
    }

    @Override
    public List<PricingPackageResponse> getAllPackages() {
        return packageRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PricingPackageResponse getPackageById(Long id) {
        PricingPackage pkg = packageRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Paket bulunamadı: " + id)
                ));
        return toResponse(pkg);
    }

    @Override
    @Transactional
    public PricingPackageResponse createPackage(PricingPackageRequest request) {
        // Kod kontrolü
        if (packageRepository.existsByPackageCode(request.getPackageCode())) {
            throw new BaseException(
                    new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Bu paket kodu zaten kullanılıyor: " + request.getPackageCode())
            );
        }

        PricingPackage pkg = PricingPackage.builder()
                .packageCode(request.getPackageCode())
                .iconName(request.getIconName())
                .badgeText(request.getBadgeText())
                .name(request.getName())
                .tagline(request.getTagline())
                .originalPrice(request.getOriginalPrice())
                .currentPrice(request.getCurrentPrice())
                .priceNote(request.getPriceNote())
                .discountPercentage(request.getDiscountPercentage())
                .deliveryTime(request.getDeliveryTime())
                .revisionCount(request.getRevisionCount())
                .supportDays(request.getSupportDays())
                .isFeatured(request.getIsFeatured())
                .displayOrder(request.getDisplayOrder())
                .status(request.getStatus())
                .build();

        PricingPackage savedPkg = packageRepository.save(pkg);

        // Features ekle
        if (request.getFeatures() != null) {
            request.getFeatures().forEach(featReq -> {
                PackageFeature feature = PackageFeature.builder()
                        .pricingPackage(savedPkg)
                        .featureText(featReq.getFeatureText())
                        .isMainFeature(featReq.getIsMainFeature())
                        .displayOrder(featReq.getDisplayOrder())
                        .build();
                savedPkg.addFeature(feature);
            });
        }

        // Notes ekle
        if (request.getNotes() != null) {
            request.getNotes().forEach(noteReq -> {
                PackageNote note = PackageNote.builder()
                        .pricingPackage(savedPkg)
                        .noteType(noteReq.getNoteType())
                        .noteText(noteReq.getNoteText())
                        .build();
                savedPkg.addNote(note);
            });
        }

        PricingPackage finalPkg = packageRepository.save(savedPkg);
        return toResponse(finalPkg);
    }

    @Override
    @Transactional
    public PricingPackageResponse updatePackage(Long id, PricingPackageRequest request) {
        PricingPackage pkg = packageRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Paket bulunamadı: " + id)
                ));

        // Kod değişiyorsa ve başka pakette kullanılıyorsa hata
        if (!pkg.getPackageCode().equals(request.getPackageCode())
                && packageRepository.existsByPackageCode(request.getPackageCode())) {
            throw new BaseException(
                    new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Bu paket kodu zaten kullanılıyor: " + request.getPackageCode())
            );
        }

        pkg.setPackageCode(request.getPackageCode());
        pkg.setIconName(request.getIconName());
        pkg.setBadgeText(request.getBadgeText());
        pkg.setName(request.getName());
        pkg.setTagline(request.getTagline());
        pkg.setOriginalPrice(request.getOriginalPrice());
        pkg.setCurrentPrice(request.getCurrentPrice());
        pkg.setPriceNote(request.getPriceNote());
        pkg.setDiscountPercentage(request.getDiscountPercentage());
        pkg.setDeliveryTime(request.getDeliveryTime());
        pkg.setRevisionCount(request.getRevisionCount());
        pkg.setSupportDays(request.getSupportDays());
        pkg.setIsFeatured(request.getIsFeatured());
        pkg.setDisplayOrder(request.getDisplayOrder());
        pkg.setStatus(request.getStatus());

        // Eski features ve notes'ları sil
        featureRepository.deleteByPricingPackageId(id);
        noteRepository.deleteByPricingPackageId(id);
        pkg.getFeatures().clear();
        pkg.getNotes().clear();

        // Yeni features ekle
        if (request.getFeatures() != null) {
            request.getFeatures().forEach(featReq -> {
                PackageFeature feature = PackageFeature.builder()
                        .pricingPackage(pkg)
                        .featureText(featReq.getFeatureText())
                        .isMainFeature(featReq.getIsMainFeature())
                        .displayOrder(featReq.getDisplayOrder())
                        .build();
                pkg.addFeature(feature);
            });
        }

        // Yeni notes ekle
        if (request.getNotes() != null) {
            request.getNotes().forEach(noteReq -> {
                PackageNote note = PackageNote.builder()
                        .pricingPackage(pkg)
                        .noteType(noteReq.getNoteType())
                        .noteText(noteReq.getNoteText())
                        .build();
                pkg.addNote(note);
            });
        }

        PricingPackage saved = packageRepository.save(pkg);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void deletePackage(Long id) {
        if (!packageRepository.existsById(id)) {
            throw new BaseException(
                    new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Paket bulunamadı: " + id)
            );
        }
        packageRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PricingPackageResponse updateDisplayOrder(Long id, Integer order) {
        PricingPackage pkg = packageRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.RESOURCE_NOT_FOUND, "Paket bulunamadı: " + id)
                ));

        pkg.setDisplayOrder(order);
        PricingPackage saved = packageRepository.save(pkg);
        return toResponse(saved);
    }

    // Mapper
    private PricingPackageResponse toResponse(PricingPackage pkg) {
        return PricingPackageResponse.builder()
                .id(pkg.getId())
                .packageCode(pkg.getPackageCode())
                .iconName(pkg.getIconName())
                .badgeText(pkg.getBadgeText())
                .name(pkg.getName())
                .tagline(pkg.getTagline())
                .originalPrice(pkg.getOriginalPrice())
                .currentPrice(pkg.getCurrentPrice())
                .priceNote(pkg.getPriceNote())
                .discountPercentage(pkg.getDiscountPercentage())
                .deliveryTime(pkg.getDeliveryTime())
                .revisionCount(pkg.getRevisionCount())
                .supportDays(pkg.getSupportDays())
                .isFeatured(pkg.getIsFeatured())
                .displayOrder(pkg.getDisplayOrder())
                .status(pkg.getStatus())
                .features(pkg.getFeatures() != null ? pkg.getFeatures().stream()
                        .map(f -> PackageFeatureResponse.builder()
                                .id(f.getId())
                                .featureText(f.getFeatureText())
                                .isMainFeature(f.getIsMainFeature())
                                .displayOrder(f.getDisplayOrder())
                                .build())
                        .collect(Collectors.toList()) : new ArrayList<>())
                .notes(pkg.getNotes() != null ? pkg.getNotes().stream()
                        .map(n -> PackageNoteResponse.builder()
                                .id(n.getId())
                                .noteType(n.getNoteType())
                                .noteText(n.getNoteText())
                                .build())
                        .collect(Collectors.toList()) : new ArrayList<>())
                .createdAt(pkg.getCreatedAt())
                .updatedAt(pkg.getUpdatedAt())
                .build();
    }
}