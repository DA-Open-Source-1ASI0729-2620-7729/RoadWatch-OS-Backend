package com.vianexo.roadwatch.reportscompliance.domain.model.valueobjects;

/**
 * Compliance status of a single project. It is calculeted on request and not stored.
 *
 * @param projectId            project identifier
 * @param projectName          project name
 * @param contractor           contractor responsible for the project
 * @param compliancePercentage percentage of required documents already uploaded
 * @param requiredDocuments    total number of required documets
 * @param pendingDocuments     number of documents not uploaded yet
 * @param openIncidents        number of open incidets
 * @param criticalIncidents    number of criticel incidents
 */

public record ProjectCompliance(
        Long projectId,
        String projectName,
        String contractor,
        int compliancePercentage,
        int requiredDocuments,
        int pendingDocuments,
        int openIncidents,
        int criticalIncidents) {
}