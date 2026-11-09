package com.vianexo.roadwatch.projectmanagement.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.projectmanagement.domain.model.valueobjects.ProjectStatus;
import com.vianexo.roadwatch.projectmanagement.infrastructure.persistence.jpa.entities.ProjectJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectJpaRepository extends JpaRepository<ProjectJpaEntity, Long> {

    List<ProjectJpaEntity> findAllByStatus(ProjectStatus status);
}
