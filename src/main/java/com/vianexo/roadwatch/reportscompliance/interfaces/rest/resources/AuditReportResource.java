package com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record AuditReportResource(
        Long id,
        Long projectId,
        String projectName,
        LocalDate periodStart,
        LocalDate periodEnd,
        String status,
        LocalDateTime generatedAt,
        List<String> sections,
        String fileUrl) {
}
