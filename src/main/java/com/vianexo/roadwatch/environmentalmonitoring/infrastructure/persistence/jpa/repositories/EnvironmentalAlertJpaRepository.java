package com.vianexo.roadwatch.environmentalmonitoring.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalAlert;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.ProjectId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnvironmentalAlertJpaRepository extends JpaRepository<EnvironmentalAlert, Long> {
    List<EnvironmentalAlert> findAllByProjectIdOrderByTriggeredAtDesc(ProjectId projectId);
}