package com.vianexo.roadwatch.environmentalmonitoring.infrastructure.persistence.jpa.adapters;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalMeasurement;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.MonitoringPointId;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.ProjectId;
import com.vianexo.roadwatch.environmentalmonitoring.domain.repositories.EnvironmentalMeasurementRepository;
import com.vianexo.roadwatch.environmentalmonitoring.infrastructure.persistence.jpa.repositories.EnvironmentalMeasurementJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Repository
public class EnvironmentalMeasurementRepositoryImpl implements EnvironmentalMeasurementRepository {

    private final EnvironmentalMeasurementJpaRepository jpaRepository;

    public EnvironmentalMeasurementRepositoryImpl(EnvironmentalMeasurementJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public EnvironmentalMeasurement save(EnvironmentalMeasurement measurement) {
        return jpaRepository.save(measurement);
    }

    @Override
    public Optional<EnvironmentalMeasurement> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<EnvironmentalMeasurement> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<EnvironmentalMeasurement> findAllByProjectIdOrderByMeasuredAtDesc(ProjectId projectId) {
        return jpaRepository.findAllByProjectIdOrderByMeasuredAtDesc(projectId);
    }

    @Override
    public List<EnvironmentalMeasurement> findAllByMonitoringPointIdOrderByMeasuredAtDesc(MonitoringPointId monitoringPointId) {
        return jpaRepository.findAllByMonitoringPointIdOrderByMeasuredAtDesc(monitoringPointId);
    }

    @Override
    public List<EnvironmentalMeasurement> findAllByMonitoringPointIdAndMeasuredAtBetweenOrderByMeasuredAtDesc(
            MonitoringPointId monitoringPointId, LocalDateTime startDate, LocalDateTime endDate) {
        return jpaRepository.findAllByMonitoringPointIdAndMeasuredAtBetweenOrderByMeasuredAtDesc(
                monitoringPointId, startDate, endDate);
    }
}
