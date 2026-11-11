package com.vianexo.roadwatch.reportscompliance.interfaces.rest.transform;

import com.vianexo.roadwatch.reportscompliance.domain.model.aggregates.AuditReport;
import com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources.AuditReportResource;

public final class AuditReportResourceFromEntityAssembler {

    private AuditReportResourceFromEntityAssembler() {
    }

    public static AuditReportResource toResourceFrom(AuditReport report) {
        return new AuditReportResource(
                report.getId(),
                report.getProjectId(),
                report.getProjectName(),
                report.getPeriodStart(),
                report.getPeriodEnd(),
                report.getStatus().name(),
                report.getGeneratedAt(),
                report.getSections(),
                report.getFileUrl());
    }
}
