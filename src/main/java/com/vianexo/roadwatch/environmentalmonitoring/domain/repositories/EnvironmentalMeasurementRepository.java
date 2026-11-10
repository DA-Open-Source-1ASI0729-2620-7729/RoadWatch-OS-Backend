package com.vianexo.roadwatch.environmentalmonitoring.domain.repositories;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalMeasurement;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.MonitoringPointId;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.ProjectId;

import java.util.List;
import java.util.Optional;

public interface EnvironmentalMeasurementRepository {
    EnvironmentalMeasurement save(EnvironmentalMeasurement measurement);
    Optional<EnvironmentalMeasurement> findById(Long id);
    List<EnvironmentalMeasurement> findAll();
    List<EnvironmentalMeasurement> findAllByProjectIdOrderByMeasuredAtDesc(ProjectId projectId);

    List<EnvironmentalMeasurement> findAllByMonitoringPointIdOrderByMeasuredAtDesc( MonitoringPointId monitoringPointId);
}