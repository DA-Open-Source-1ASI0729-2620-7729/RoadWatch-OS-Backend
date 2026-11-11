package com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.resources;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.IndicatorType;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.MeasurementStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EnvironmentalMeasurementResource(
        Long id,
        Long monitoringPointId,
        IndicatorType indicatorType,
        BigDecimal value,
        LocalDateTime measuredAt,
        MeasurementStatus status
) {}
