package com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.transform;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.RegisterEnvironmentalMeasurementCommand;
import com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.resources.RegisterEnvironmentalMeasurementResource;

public class RegisterEnvironmentalMeasurementCommandFromResourceAssembler {
    public static RegisterEnvironmentalMeasurementCommand toCommandFromResource(RegisterEnvironmentalMeasurementResource resource) {
        return new RegisterEnvironmentalMeasurementCommand(
                resource.projectId(),
                resource.monitoringPointId(),
                resource.indicatorType(),
                resource.measuredValue(),
                resource.unit(),
                resource.measuredAt(),
                resource.registeredByUserId(),
                resource.observation()
        );
    }
}