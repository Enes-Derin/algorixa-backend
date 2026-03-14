// ProjectTechStackRepository.java
package com.enesderin.softvra_backend.repo;

import com.enesderin.softvra_backend.model.ProjectTechStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTechStackRepository extends JpaRepository<ProjectTechStack, Long> {
    void deleteByProjectId(Long projectId);
}