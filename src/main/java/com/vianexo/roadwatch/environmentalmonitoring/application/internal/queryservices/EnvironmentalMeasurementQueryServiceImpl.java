package com.vianexo.roadwatch.environmentalmonitoring.application.internal.queryservices;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalMeasurement;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.*;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.MonitoringPointId;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.ProjectId;
import com.vianexo.roadwatch.environmentalmonitoring.domain.services.EnvironmentalMeasurementQueryService;
import com.vianexo.roadwatch.environmentalmonitoring.infrastructure.persistence.jpa.repositories.EnvironmentalMeasurementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnvironmentalMeasurementQueryServiceImpl implements EnvironmentalMeasurementQueryService {

    private final EnvironmentalMeasurementRepository measurementRepository;

    public EnvironmentalMeasurementQueryServiceImpl(EnvironmentalMeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    @Override
    public List<EnvironmentalMeasurement> handle(GetAllEnvironmentalMeasurementsQuery query) {
        return measurementRepository.findAll();
    }

    @Override
    public Optional<EnvironmentalMeasurement> handle(GetEnvironmentalMeasurementByIdQuery query) {
        return measurementRepository.findById(query.measurementId());
    }

    @Override
    public List<EnvironmentalMeasurement> handle(GetEnvironmentalMeasurementsByProjectIdQuery query) {
        return measurementRepository.findAllByProjectIdOrderByMeasuredAtDesc(new ProjectId(query.projectId()));
    }

    @Override
    public List<EnvironmentalMeasurement> handle(GetEnvironmentalMeasurementsByMonitoringPointIdQuery query) {
        return measurementRepository.findAllByMonitoringPointIdOrderByMeasuredAtDesc(
                new MonitoringPointId(query.monitoringPointId())
        );
    }
}