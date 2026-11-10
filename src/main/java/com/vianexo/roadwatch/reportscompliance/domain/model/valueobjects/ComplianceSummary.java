package com.vianexo.roadwatch.reportscompliance.domain.model.valueobjects;

/**
 * Overall compliance summary. It is calculated on request and not store.
 *
 * @param compliancePercentage   percentage of required documents already uploaded
 * @param requiredDocuments      total nunber of required documents
 * @param pendingDocuments       number of documets not uploaded yet
 * @param openIncidents          number of open incidents
 * @param criticalIncidents      number of critical incidets
 * @param averageResolutionHours average hours to resolve an incident, or null if unknown
 */

public record ComplianceSummary(
        int compliancePercentage,
        int requiredDocuments,
        int pendingDocuments,
        int openIncidents,
        int criticalIncidents,
        Double averageResolutionHours) {
}