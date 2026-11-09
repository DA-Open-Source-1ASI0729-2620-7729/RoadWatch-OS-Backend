package com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.IndicatorType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RegisterEnvironmentalMeasurementCommand(
        Long projectId,
        Long monitoringPointId,
        IndicatorType indicatorType,
        BigDecimal measuredValue,
        String unit,
        LocalDateTime measuredAt,
        Long registeredByUserId,
        String observation
) {
    public RegisterEnvironmentalMeasurementCommand {
        if (projectId == null || projectId <= 0) {
            throw new IllegalArgumentException("projectId must be positive");
        }
        if (monitoringPointId == null || monitoringPointId <= 0) {
            throw new IllegalArgumentException("monitoringPointId must be positive");
        }
        if (indicatorType == null) {
            throw new IllegalArgumentException("indicatorType is required");
        }
        if (measuredValue == null) {
            throw new IllegalArgumentException("measuredValue is required");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("unit is required");
        }
        if (registeredByUserId == null || registeredByUserId <= 0) {
            throw new IllegalArgumentException("registeredByUserId must be positive");
        }
    }
}