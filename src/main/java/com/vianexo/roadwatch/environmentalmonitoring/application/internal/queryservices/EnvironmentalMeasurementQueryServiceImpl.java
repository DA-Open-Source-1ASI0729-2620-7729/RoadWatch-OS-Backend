package com.vianexo.roadwatch.environmentalmonitoring.application.internal.queryservices;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalMeasurement;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetAllEnvironmentalMeasurementsQuery;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetEnvironmentalMeasurementByIdQuery;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetEnvironmentalMeasurementsByMonitoringPointIdQuery;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetEnvironmentalMeasurementsByDateRangeQuery;
import com.vianexo.roadwatch.environmentalmonitoring.domain.services.EnvironmentalMeasurementQueryService;
import com.vianexo.roadwatch.environmentalmonitoring.domain.repositories.EnvironmentalMeasurementRepository;
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
    public List<EnvironmentalMeasurement> handle(GetEnvironmentalMeasurementsByMonitoringPointIdQuery query) {
        return measurementRepository.findAllByMonitoringPointIdOrderByMeasuredAtDesc(query.monitoringPointId());
    }

    @Override
    public List<EnvironmentalMeasurement> handle(GetEnvironmentalMeasurementsByDateRangeQuery query) {
        return measurementRepository.findAllByMonitoringPointIdAndMeasuredAtBetweenOrderByMeasuredAtDesc(
                query.monitoringPointId(),
                query.startDate().atStartOfDay(),
                query.endDate().plusDays(1).atStartOfDay().minusNanos(1)
        );
    }
}
