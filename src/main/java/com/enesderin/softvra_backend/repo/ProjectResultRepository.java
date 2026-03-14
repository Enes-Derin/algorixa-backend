// ProjectResultRepository.java
package com.enesderin.softvra_backend.repo;

import com.enesderin.softvra_backend.model.ProjectResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectResultRepository extends JpaRepository<ProjectResult, Long> {
    void deleteByProjectId(Long projectId);
}