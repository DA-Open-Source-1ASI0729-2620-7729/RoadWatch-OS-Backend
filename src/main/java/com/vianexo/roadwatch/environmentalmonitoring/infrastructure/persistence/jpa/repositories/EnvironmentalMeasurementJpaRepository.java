package com.vianexo.roadwatch.environmentalmonitoring.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDateTime;


public interface EnvironmentalMeasurementJpaRepository extends JpaRepository<EnvironmentalMeasurement, Long> {
    List<EnvironmentalMeasurement> findAllByMonitoringPointIdOrderByMeasuredAtDesc(Long monitoringPointId);
    List<EnvironmentalMeasurement> findAllByMonitoringPointIdAndMeasuredAtBetweenOrderByMeasuredAtDesc(
            Long monitoringPointId, LocalDateTime startDate, LocalDateTime endDate);
}
