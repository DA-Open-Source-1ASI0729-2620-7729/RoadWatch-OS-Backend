package com.vianexo.roadwatch.reportscompliance.interfaces.rest.transform;

import com.vianexo.roadwatch.reportscompliance.domain.model.entities.ComplianceSummary;
import com.vianexo.roadwatch.reportscompliance.interfaces.rest.resources.ComplianceSummaryResource;

public final class ComplianceSummaryResourceFromSummaryAssembler {

    private ComplianceSummaryResourceFromSummaryAssembler() {
    }

    public static ComplianceSummaryResource toResourceFrom(ComplianceSummary summary) {
        return new ComplianceSummaryResource(
                summary.getId(),
                summary.getProjectId(),
                summary.getCompliantMeasurements(),
                summary.getNonCompliantMeasurements(),
                summary.getCompliancePercentage(),
                summary.getCalculatedAt());
    }
}
