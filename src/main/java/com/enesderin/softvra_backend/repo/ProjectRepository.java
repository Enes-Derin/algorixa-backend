package com.enesderin.softvra_backend.repo;

import com.enesderin.softvra_backend.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
}
