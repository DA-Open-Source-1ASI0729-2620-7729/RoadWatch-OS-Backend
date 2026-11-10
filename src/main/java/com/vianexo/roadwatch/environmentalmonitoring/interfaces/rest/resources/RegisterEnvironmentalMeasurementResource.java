package com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.resources;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.IndicatorType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RegisterEnvironmentalMeasurementResource(
        Long projectId,
        Long monitoringPointId,
        IndicatorType indicatorType,
        BigDecimal measuredValue,
        String unit,
        LocalDateTime measuredAt,
        Long registeredByUserId,
        String observation
) {}