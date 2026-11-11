package com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources;

import java.time.LocalDateTime;

public record ComplianceSummaryResource(
        Long id,
        Long projectId,
        Integer compliantMeasurements,
        Integer nonCompliantMeasurements,
        Double compliancePercentage,
        LocalDateTime calculatedAt) {
}
