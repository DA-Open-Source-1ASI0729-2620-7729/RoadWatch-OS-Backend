package com.vianexo.roadwatch.reportscompliance.application.services;

import com.vianexo.roadwatch.documentevidence.interfaces.acl.DocumentEvidenceContextFacade;
import com.vianexo.roadwatch.reportscompliance.application.outboundservices.ComplianceDataProvider;
import com.vianexo.roadwatch.reportscompliance.domain.model.entities.ComplianceSummary;
import com.vianexo.roadwatch.reportscompliance.domain.model.valueobjects.ProjectCompliance;
import com.vianexo.roadwatch.reportscompliance.domain.repositories.ComplianceSummaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplianceService {

    private final DocumentEvidenceContextFacade documentFacade;
    private final ComplianceDataProvider dataProvider;
    private final ComplianceSummaryRepository summaryRepository;

    public ComplianceService(DocumentEvidenceContextFacade documentFacade,
                             ComplianceDataProvider dataProvider,
                             ComplianceSummaryRepository summaryRepository) {
        this.documentFacade = documentFacade;
        this.dataProvider = dataProvider;
        this.summaryRepository = summaryRepository;
    }

    public ComplianceSummary recalculateCompliance(Long projectId) {
        var summary = summaryRepository.findByProjectId(projectId)
                .orElseGet(() -> new ComplianceSummary(projectId));
        summary.recalculate(dataProvider.countCompliantMeasurements(projectId),
                dataProvider.countNonCompliantMeasurements(projectId));
        return summaryRepository.save(summary);
    }

    public List<ComplianceSummary> findAll() {
        return summaryRepository.findAll();
    }

    public List<ProjectCompliance> getProjects() {
        return documentFacade.findProjectIds().stream()
                .map(this::toProjectCompliance)
                .toList();
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
