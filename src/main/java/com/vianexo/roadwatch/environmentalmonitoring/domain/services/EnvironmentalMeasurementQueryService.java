package com.vianexo.roadwatch.environmentalmonitoring.domain.services;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalMeasurement;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface EnvironmentalMeasurementQueryService {
    List<EnvironmentalMeasurement> handle(GetAllEnvironmentalMeasurementsQuery query);
    Optional<EnvironmentalMeasurement> handle(GetEnvironmentalMeasurementByIdQuery query);
    List<EnvironmentalMeasurement> handle(GetEnvironmentalMeasurementsByMonitoringPointIdQuery query);
    List<EnvironmentalMeasurement> handle(GetEnvironmentalMeasurementsByDateRangeQuery query);
}
