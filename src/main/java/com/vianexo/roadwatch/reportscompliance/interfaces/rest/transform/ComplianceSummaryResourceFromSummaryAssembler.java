package com.vianexo.roadwatch.reportscompliance.interfaces.rest.transform;

import com.vianexo.roadwatch.reportscompliance.domain.model.valueobjects.ComplianceSummary;
import com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources.ComplianceSummaryResource;

public final class ComplianceSummaryResourceFromSummaryAssembler {

    private ComplianceSummaryResourceFromSummaryAssembler() {
    }

    public static ComplianceSummaryResource toResourceFrom(ComplianceSummary summary) {
        return new ComplianceSummaryResource(
                summary.compliancePercentage(),
                summary.requiredDocuments(),
                summary.pendingDocuments(),
                summary.openIncidents(),
                summary.criticalIncidents(),
                summary.averageResolutionHours());
    }
}