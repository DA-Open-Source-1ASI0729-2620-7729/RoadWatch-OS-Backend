package com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.resources;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.IndicatorType;

import java.math.BigDecimal;

public record NormativeThresholdResource(
        Long id,
        IndicatorType indicatorType,
        String unit,
        BigDecimal warningLimit,
        BigDecimal maximumLimit
) {}
