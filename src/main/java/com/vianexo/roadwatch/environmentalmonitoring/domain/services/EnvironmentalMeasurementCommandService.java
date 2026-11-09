package com.vianexo.roadwatch.environmentalmonitoring.domain.services;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalMeasurement;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.RegisterEnvironmentalMeasurementCommand;

import java.util.Optional;

public interface EnvironmentalMeasurementCommandService {
    Optional<EnvironmentalMeasurement> handle(RegisterEnvironmentalMeasurementCommand command);
}