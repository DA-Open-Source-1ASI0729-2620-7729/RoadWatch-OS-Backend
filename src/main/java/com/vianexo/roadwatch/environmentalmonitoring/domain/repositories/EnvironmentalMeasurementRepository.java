package com.vianexo.roadwatch.environmentalmonitoring.domain.repositories;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalMeasurement;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

public interface EnvironmentalMeasurementRepository {
    EnvironmentalMeasurement save(EnvironmentalMeasurement measurement);
    Optional<EnvironmentalMeasurement> findById(Long id);
    List<EnvironmentalMeasurement> findAll();
    List<EnvironmentalMeasurement> findAllByMonitoringPointIdOrderByMeasuredAtDesc(Long monitoringPointId);
    List<EnvironmentalMeasurement> findAllByMonitoringPointIdAndMeasuredAtBetweenOrderByMeasuredAtDesc(
            Long monitoringPointId, LocalDateTime startDate, LocalDateTime endDate);
}
