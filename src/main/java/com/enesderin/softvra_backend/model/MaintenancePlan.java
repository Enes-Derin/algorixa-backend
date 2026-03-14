// MaintenancePlan.java
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
@Table(name = "maintenance_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenancePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_code", unique = true, length = 50)
    private String planCode; // 'LITE','PRO','COMMERCE'

    @Column(name = "icon_name", length = 50)
    private String iconName;

    @Column(name = "badge_text", length = 100)
    private String badgeText;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(name = "monthly_price", precision = 10, scale = 2)
    private BigDecimal monthlyPrice;

    @Column(name = "ideal_for", columnDefinition = "TEXT")
    private String idealFor;

    @Column(name = "is_best_seller")
    private Boolean isBestSeller = false;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PackageStatus status = PackageStatus.ACTIVE;

    @OneToMany(mappedBy = "maintenancePlan", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MaintenanceFeature> features = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Helper method
    public void addFeature(MaintenanceFeature feature) {
        features.add(feature);
        feature.setMaintenancePlan(this);
    }
}