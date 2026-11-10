package com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.transform;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalAlert;
import com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.resources.EnvironmentalAlertResource;

public class EnvironmentalAlertResourceFromEntityAssembler {
    public static EnvironmentalAlertResource toResourceFromEntity(EnvironmentalAlert entity) {
        return new EnvironmentalAlertResource(
                entity.getId(),
                entity.getMeasurementId(),
                entity.getMessage(),
                entity.getCreatedAt(),
                entity.isAcknowledged()
        );
    }
}
