package com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record MonitoringPointId(Long monitoringPointId) {
    public MonitoringPointId {
        if (monitoringPointId == null || monitoringPointId <= 0) {
            throw new IllegalArgumentException("monitoringPointId must be a positive number");
        }
    }
}