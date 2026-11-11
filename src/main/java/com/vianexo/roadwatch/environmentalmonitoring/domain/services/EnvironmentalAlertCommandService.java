package com.vianexo.roadwatch.environmentalmonitoring.domain.services;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalAlert;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.AcknowledgeEnvironmentalAlertCommand;

import java.util.Optional;

public interface EnvironmentalAlertCommandService {
    Optional<EnvironmentalAlert> handle(AcknowledgeEnvironmentalAlertCommand command);
}
