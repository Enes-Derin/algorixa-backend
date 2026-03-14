// PackageNoteRepository.java
package com.enesderin.softvra_backend.repo;

import com.enesderin.softvra_backend.model.PackageNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageNoteRepository extends JpaRepository<PackageNote, Long> {

    List<PackageNote> findByPricingPackageId(Long packageId);

    void deleteByPricingPackageId(Long packageId);
}