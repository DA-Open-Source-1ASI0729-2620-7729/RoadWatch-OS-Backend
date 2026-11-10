package com.vianexo.roadwatch.reportscompliance.application.services;

import com.vianexo.roadwatch.documentevidence.interfaces.acl.DocumentEvidenceContextFacade;
import com.vianexo.roadwatch.reportscompliance.application.outboundservices.ComplianceDataProvider;
import com.vianexo.roadwatch.reportscompliance.domain.model.valueobjects.ComplianceSummary;
import com.vianexo.roadwatch.reportscompliance.domain.model.valueobjects.EnvironmentalKpi;
import com.vianexo.roadwatch.reportscompliance.domain.model.valueobjects.ProjectCompliance;
import com.vianexo.roadwatch.reportscompliance.domain.repositories.AuditReportRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ComplianceService {

    private final DocumentEvidenceContextFacade documentFacade;
    private final ComplianceDataProvider dataProvider;
    private final AuditReportRepository reportRepository;

    public ComplianceService(DocumentEvidenceContextFacade documentFacade,
                             ComplianceDataProvider dataProvider,
                             AuditReportRepository reportRepository) {
        this.documentFacade = documentFacade;
        this.dataProvider = dataProvider;
        this.reportRepository = reportRepository;
    }

    public ComplianceSummary getSummary() {
        var projectIds = documentFacade.findProjectIds();
        var required = documentFacade.countRequiredDocuments();
        var pending = documentFacade.countPendingDocuments();
        var openIncidents = projectIds.stream().mapToInt(dataProvider::countOpenIncidents).sum();
        var criticalIncidents = projectIds.stream().mapToInt(dataProvider::countCriticalIncidents).sum();
        return new ComplianceSummary(
                calculatePercentage(required, pending),
                required,
                pending,
                openIncidents,
                criticalIncidents,
                dataProvider.averageResolutionHours());
    }

    public List<ProjectCompliance> getProjects() {
        return documentFacade.findProjectIds().stream()
                .map(this::toProjectCompliance)
                .toList();
    }

    public List<EnvironmentalKpi> getKpis() {
        var summary = getSummary();
        var now = Instant.now();
        var generatedReports = reportRepository.findAll().size();
        var uploadedDocuments = summary.requiredDocuments() - summary.pendingDocuments();
        return List.of(
                new EnvironmentalKpi("compliance", "Environmental compliance",
                        summary.compliancePercentage() + "%", "percentage", now),
                new EnvironmentalKpi("documents", "Required documents",
                        uploadedDocuments + " / " + summary.requiredDocuments(), "documents", now),
                new EnvironmentalKpi("incidents", "Open incidents",
                        String.valueOf(summary.openIncidents()), "incidents", now),
                new EnvironmentalKpi("reports", "Generated reports",
                        String.valueOf(generatedReports), "reports", now));
    }

    private ProjectCompliance toProjectCompliance(Long projectId) {
        var required = documentFacade.countRequiredDocuments(projectId);
        var pending = documentFacade.countPendingDocuments(projectId);
        return new ProjectCompliance(
                projectId,
                dataProvider.findProjectName(projectId),
                dataProvider.findContractor(projectId),
                calculatePercentage(required, pending),
                required,
                pending,
                dataProvider.countOpenIncidents(projectId),
                dataProvider.countCriticalIncidents(projectId));
    }

    private int calculatePercentage(int required, int pending) {
        if (required == 0) {
            return 100;
        }
        return Math.round((required - pending) * 100f / required);
    }
}