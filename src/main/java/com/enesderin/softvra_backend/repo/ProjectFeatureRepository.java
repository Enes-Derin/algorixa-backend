// ProjectFeatureRepository.java
package com.enesderin.softvra_backend.repo;

import com.enesderin.softvra_backend.model.ProjectFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectFeatureRepository extends JpaRepository<ProjectFeature, Long> {
    void deleteByProjectId(Long projectId);
}