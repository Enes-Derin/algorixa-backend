// PackageNote.java
package com.enesderin.softvra_backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "package_notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = false)
    private PricingPackage pricingPackage;

    @Enumerated(EnumType.STRING)
    @Column(name = "note_type", length = 20)
    private NoteType noteType = NoteType.NEUTRAL;

    @Column(name = "note_text", columnDefinition = "TEXT", nullable = false)
    private String noteText;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}