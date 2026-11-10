package com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources;

public record ComplianceSummaryResource(
        int compliancePercentage,
        int requiredDocuments,
        int pendingDocuments,
        int openIncidents,
        int criticalIncidents,
        Double averageResolutionHours) {
}