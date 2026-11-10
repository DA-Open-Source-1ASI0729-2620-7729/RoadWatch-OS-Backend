package com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.IndicatorType;

import java.math.BigDecimal;

public record UpsertNormativeThresholdCommand(
        IndicatorType indicatorType,
        String unit,
        BigDecimal warningLimit,
        BigDecimal maximumLimit
) {
    public UpsertNormativeThresholdCommand {
        if (indicatorType == null) {
            throw new IllegalArgumentException("indicatorType is required");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("unit is required");
        }
    }
}
