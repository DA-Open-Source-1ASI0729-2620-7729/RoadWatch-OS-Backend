package com.vianexo.roadwatch.environmentalmonitoring.domain.services;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalMeasurement;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.RegisterEnvironmentalMeasurementCommand;

public interface EnvironmentalMeasurementCommandService {

    EnvironmentalMeasurement handle(RegisterEnvironmentalMeasurementCommand command);
}
