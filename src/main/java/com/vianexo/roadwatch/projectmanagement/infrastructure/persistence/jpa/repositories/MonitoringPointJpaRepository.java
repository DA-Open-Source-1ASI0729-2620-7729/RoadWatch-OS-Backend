package com.vianexo.roadwatch.projectmanagement.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.projectmanagement.infrastructure.persistence.jpa.entities.MonitoringPointJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitoringPointJpaRepository extends JpaRepository<MonitoringPointJpaEntity, Long> {

    List<MonitoringPointJpaEntity> findByProject_Id(Long projectId);

}
