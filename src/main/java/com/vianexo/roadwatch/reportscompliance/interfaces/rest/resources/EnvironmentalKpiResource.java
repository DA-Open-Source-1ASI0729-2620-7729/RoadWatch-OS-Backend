package com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources;

import java.time.LocalDateTime;

public record EnvironmentalKpiResource(
        Long id,
        Long projectId,
        Double compliancePercentage,
        Double averageResolutionTime,
        Integer openIncidents,
        Integer criticalIncidents,
        LocalDateTime calculatedAt) {
}
