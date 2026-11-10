package com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.resources;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.AlertSeverity;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.AlertStatus;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.IndicatorType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EnvironmentalAlertResource(
        Long id,
        Long projectId,
        Long monitoringPointId,
        Long measurementId,
        IndicatorType indicatorType,
        BigDecimal recordedValue,
        BigDecimal thresholdLimit,
        AlertSeverity severity,
        AlertStatus status,
        boolean acknowledged,
        String message,
        LocalDateTime triggeredAt
) {}
