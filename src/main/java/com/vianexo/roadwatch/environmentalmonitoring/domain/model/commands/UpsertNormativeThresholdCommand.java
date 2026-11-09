package com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.IndicatorType;

import java.math.BigDecimal;

public record UpsertNormativeThresholdCommand(
        Long projectId,
        IndicatorType indicatorType,
        String unit,
        BigDecimal warningLimit,
        BigDecimal maxPermissibleLimit,
        String normativeReference
) {
    public UpsertNormativeThresholdCommand {
        if (projectId == null || projectId <= 0) {
            throw new IllegalArgumentException("projectId must be positive");
        }
        if (indicatorType == null) {
            throw new IllegalArgumentException("indicatorType is required");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("unit is required");
        }
        if (normativeReference == null || normativeReference.isBlank()) {
            throw new IllegalArgumentException("normativeReference is required");
        }
    }
}