package com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.transform;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalMeasurement;
import com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.resources.EnvironmentalMeasurementResource;

public class EnvironmentalMeasurementResourceFromEntityAssembler {
    public static EnvironmentalMeasurementResource toResourceFromEntity(EnvironmentalMeasurement entity) {
        return new EnvironmentalMeasurementResource(
                entity.getId(),
                entity.getMonitoringPointId(),
                entity.getIndicatorType(),
                entity.getValue(),
                entity.getMeasuredAt(),
                entity.getStatus()
        );
    }
}
