package com.vianexo.roadwatch.reportscompliance.domain.model.valueobjects;

import java.time.Instant;

/**
 * A key performance indicato shown in the compliance dashboard. It is calculated on request and not stored.
 *
 * @param code         short identifier, such as "compliance" or "reports"
 * @param metricName   readable name of the indicator
 * @param value        value as text, such as "82%" or "20 / 24"
 * @param unit         unit of the value, such as "percentage" or "documents"
 * @param calculatedAt moment when the value was calculated
 */
public record EnvironmentalKpi(
        String code,
        String metricName,
        String value,
        String unit,
        Instant calculatedAt
) {
}