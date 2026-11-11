package com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources;

public record ProjectReportResource(
        Long projectId,
        String projectName,
        String contractor,
        int compliancePercentage,
        int requiredDocuments,
        int pendingDocuments,
        int openIncidents,
        int criticalIncidents) {
}