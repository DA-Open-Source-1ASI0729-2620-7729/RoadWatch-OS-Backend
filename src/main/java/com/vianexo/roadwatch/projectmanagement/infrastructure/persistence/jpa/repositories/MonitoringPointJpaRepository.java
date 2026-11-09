package com.vianexo.roadwatch.projectmanagement.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.projectmanagement.infrastructure.persistence.jpa.entities.MonitoringPointJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MonitoringPointJpaRepository extends JpaRepository<MonitoringPointJpaEntity, Long> {

    List<MonitoringPointJpaEntity> findByProject_Id(Long projectId);

    Page<MonitoringPointJpaEntity> findByProject_Id(Long projectId, Pageable pageable);
}
