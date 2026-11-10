package com.vianexo.roadwatch.reportscompliance.interfaces.rest.transform;

import com.vianexo.roadwatch.reportscompliance.domain.model.valueobjects.ProjectCompliance;
import com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources.ProjectReportResource;

public final class ProjectReportResourceFromProjectComplianceAssembler {

    private ProjectReportResourceFromProjectComplianceAssembler() {
    }

    public static ProjectReportResource toResourceFrom(ProjectCompliance project) {
        return new ProjectReportResource(
                project.projectId(),
                project.projectName(),
                project.contractor(),
                project.compliancePercentage(),
                project.requiredDocuments(),
                project.pendingDocuments(),
                project.openIncidents(),
                project.criticalIncidents());
    }
}