// PortfolioProjectRepository.java
package com.enesderin.softvra_backend.repo;

import com.enesderin.softvra_backend.model.PortfolioProject;
import com.enesderin.softvra_backend.model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioProjectRepository extends JpaRepository<PortfolioProject, Long> {

    List<PortfolioProject> findByStatusOrderByDisplayOrderAsc(ProjectStatus status);

    List<PortfolioProject> findByStatusAndCategoryOrderByDisplayOrderAsc(ProjectStatus status, String category);

    Optional<PortfolioProject> findByIsFeaturedTrueAndStatus(ProjectStatus status);

    List<PortfolioProject> findAllByOrderByDisplayOrderAsc();

    Long countByStatus(ProjectStatus status);
}