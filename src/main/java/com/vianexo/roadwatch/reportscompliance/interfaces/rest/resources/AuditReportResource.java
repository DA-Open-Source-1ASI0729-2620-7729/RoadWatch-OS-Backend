package com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record AuditReportResource(
        Long id,
        Long projectId,
        String projectName,
        LocalDate periodStart,
        LocalDate periodEnd,
        String status,
        LocalDate issuedAt,
        List<String> sections,
        Instant generatedAt,
        String fileUrl) {
}