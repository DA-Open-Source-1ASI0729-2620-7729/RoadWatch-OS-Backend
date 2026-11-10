package com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record CreateAuditReportResource(
        @NotNull Long projectId,
        @NotNull LocalDate periodStart,
        @NotNull LocalDate periodEnd,
        @NotEmpty List<String> sections,
        String format) {
}