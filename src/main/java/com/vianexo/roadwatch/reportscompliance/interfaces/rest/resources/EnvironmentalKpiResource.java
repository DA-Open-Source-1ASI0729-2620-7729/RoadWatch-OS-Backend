package com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources;

import java.time.Instant;

public record EnvironmentalKpiResource(
        String code,
        String metricName,
        String value,
        String unit,
        Instant calculatedAt) {
}