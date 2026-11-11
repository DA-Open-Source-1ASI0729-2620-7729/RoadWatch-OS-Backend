package com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries;

import java.time.LocalDate;

public record GetEnvironmentalMeasurementsByDateRangeQuery(
        Long monitoringPointId,
        LocalDate startDate,
        LocalDate endDate
) {
    public GetEnvironmentalMeasurementsByDateRangeQuery {
        if (monitoringPointId == null || monitoringPointId <= 0) {
            throw new IllegalArgumentException("monitoringPointId must be positive");
        }
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("A valid date range is required");
        }
    }
}
