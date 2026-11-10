package com.vianexo.roadwatch.environmentalmonitoring.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalMeasurement;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.MonitoringPointId;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.ProjectId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EnvironmentalMeasurementJpaRepository extends JpaRepository<EnvironmentalMeasurement, Long> {
    List<EnvironmentalMeasurement> findAllByProjectIdOrderByMeasuredAtDesc(ProjectId projectId);
    List<EnvironmentalMeasurement> findAllByMonitoringPointIdOrderByMeasuredAtDesc(MonitoringPointId monitoringPointId);
}