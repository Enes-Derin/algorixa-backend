// PricingPackage.java
package com.enesderin.softvra_backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pricing_packages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "package_code", unique = true, length = 50)
    private String packageCode; // 'LANDING','STATIC','DYNAMIC','CUSTOM'

    @Column(name = "icon_name", length = 50)
    private String iconName; // 'Layout','Package','Star','Cpu'

    @Column(name = "badge_text", length = 100)
    private String badgeText;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String tagline;

    @Column(name = "original_price", precision = 10, scale = 2)
    private BigDecimal originalPrice;

    @Column(name = "current_price", precision = 10, scale = 2)
    private BigDecimal currentPrice;

    @Column(name = "price_note", length = 200)
    private String priceNote;

    @Column(name = "discount_percentage")
    private Integer discountPercentage;

    @Column(name = "delivery_time", length = 100)
    private String deliveryTime;

    @Column(name = "revision_count")
    private Integer revisionCount;

    @Column(name = "support_days")
    private Integer supportDays;

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PackageStatus status = PackageStatus.ACTIVE;

    @OneToMany(mappedBy = "pricingPackage", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PackageFeature> features = new ArrayList<>();

    @OneToMany(mappedBy = "pricingPackage", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PackageNote> notes = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Helper methods
    public void addFeature(PackageFeature feature) {
        features.add(feature);
        feature.setPricingPackage(this);
    }

    public void addNote(PackageNote note) {
        notes.add(note);
        note.setPricingPackage(this);
    }
}