package com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.IndicatorType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RegisterEnvironmentalMeasurementCommand(
        Long monitoringPointId,
        IndicatorType indicatorType,
        BigDecimal value,
        LocalDateTime measuredAt
) {
    public RegisterEnvironmentalMeasurementCommand {
        if (monitoringPointId == null || monitoringPointId <= 0) {
            throw new IllegalArgumentException("monitoringPointId must be positive");
        }
        if (indicatorType == null) {
            throw new IllegalArgumentException("indicatorType is required");
        }
        if (value == null) {
            throw new IllegalArgumentException("value is required");
        }
    }
}
