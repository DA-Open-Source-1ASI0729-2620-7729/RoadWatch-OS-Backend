package com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.transform;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalMeasurement;
import com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.resources.EnvironmentalMeasurementResource;

public class EnvironmentalMeasurementResourceFromEntityAssembler {
    public static EnvironmentalMeasurementResource toResourceFromEntity(EnvironmentalMeasurement entity) {
        return new EnvironmentalMeasurementResource(
                entity.getId(),
                entity.getProjectId().projectId(),
                entity.getMonitoringPointId().monitoringPointId(),
                entity.getIndicatorType(),
                entity.getMeasuredValue(),
                entity.getThresholdApplied(),
                entity.getUnit(),
                entity.getStatus(),
                entity.getMeasuredAt(),
                entity.getRegisteredByUserId(),
                entity.getObservation()
        );
    }
}