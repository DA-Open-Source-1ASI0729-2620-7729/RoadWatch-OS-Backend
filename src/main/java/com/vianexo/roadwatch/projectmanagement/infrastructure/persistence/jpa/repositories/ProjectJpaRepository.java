package com.vianexo.roadwatch.projectmanagement.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.projectmanagement.domain.model.valueobjects.ProjectStatus;
import com.vianexo.roadwatch.projectmanagement.infrastructure.persistence.jpa.entities.ProjectJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectJpaRepository extends JpaRepository<ProjectJpaEntity, Long> {

    List<ProjectJpaEntity> findAllByStatus(ProjectStatus status);

    Page<ProjectJpaEntity> findAllByStatus(ProjectStatus status, Pageable pageable);
}
