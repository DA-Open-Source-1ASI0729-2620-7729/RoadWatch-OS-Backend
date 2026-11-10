package com.vianexo.roadwatch.reportscompliance.interfaces.rest.transform;

import com.vianexo.roadwatch.reportscompliance.domain.model.aggregates.AuditReport;
import com.vianexo.roadwatch.reportscompliance.domain.model.valueobjects.AuditReportStatus;
import com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources.AuditReportResource;

public final class AuditReportResourceFromEntityAssembler {

    private static final String PDF_URL_PREFIX = "/api/v1/reports/";

    private AuditReportResourceFromEntityAssembler() {
    }

    public static AuditReportResource toResourceFrom(AuditReport report) {
        return new AuditReportResource(
                report.getId(),
                report.getProjectId(),
                report.getProjectName(),
                report.getPeriodStart(),
                report.getPeriodEnd(),
                toLabel(report.getStatus()),
                report.getIssuedAt(),
                report.getSections(),
                report.getCreatedAt().toInstant(),
                PDF_URL_PREFIX + report.getId() + "/pdf");
    }

    private static String toLabel(AuditReportStatus status) {
        return switch (status) {
            case DRAFT -> "Borrador";
            case ISSUED -> "Emitido";
            case OBSERVED -> "Observado";
        };
    }
}