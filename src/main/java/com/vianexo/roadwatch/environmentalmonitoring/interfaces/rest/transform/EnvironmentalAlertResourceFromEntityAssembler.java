package com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.transform;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalAlert;
import com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.resources.EnvironmentalAlertResource;

public class EnvironmentalAlertResourceFromEntityAssembler {
    public static EnvironmentalAlertResource toResourceFromEntity(EnvironmentalAlert entity) {
        return new EnvironmentalAlertResource(
                entity.getId(),
                entity.getProjectId().projectId(),
                entity.getMonitoringPointId().monitoringPointId(),
                entity.getMeasurementId(),
                entity.getIndicatorType(),
                entity.getRecordedValue(),
                entity.getThresholdLimit(),
                entity.getSeverity(),
                entity.getStatus(),
                entity.getMessage(),
                entity.getTriggeredAt()
        );
    }
}