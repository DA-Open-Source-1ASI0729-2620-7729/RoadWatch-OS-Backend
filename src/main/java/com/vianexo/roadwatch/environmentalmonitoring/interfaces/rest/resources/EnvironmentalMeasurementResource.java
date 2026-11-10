package com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.resources;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.IndicatorType;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.MeasurementStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EnvironmentalMeasurementResource(
        Long id,
        Long projectId,
        Long monitoringPointId,
        IndicatorType indicatorType,
        BigDecimal measuredValue,
        BigDecimal thresholdApplied,
        String unit,
        MeasurementStatus status,
        LocalDateTime measuredAt,
        Long registeredByUserId,
        String observation
) {}