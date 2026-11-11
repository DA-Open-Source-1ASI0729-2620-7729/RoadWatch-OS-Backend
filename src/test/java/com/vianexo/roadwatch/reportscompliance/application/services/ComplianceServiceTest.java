package com.vianexo.roadwatch.reportscompliance.application.services;

import com.vianexo.roadwatch.documentevidence.interfaces.acl.DocumentEvidenceContextFacade;
import com.vianexo.roadwatch.reportscompliance.application.outboundservices.ComplianceDataProvider;
import com.vianexo.roadwatch.reportscompliance.domain.model.entities.ComplianceSummary;
import com.vianexo.roadwatch.reportscompliance.domain.repositories.ComplianceSummaryRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ComplianceServiceTest {

    private final ComplianceSummaryRepository summaryRepository = mock(ComplianceSummaryRepository.class);
    private final ComplianceDataProvider dataProvider = mock(ComplianceDataProvider.class);
    private final ComplianceService complianceService = new ComplianceService(
            mock(DocumentEvidenceContextFacade.class), dataProvider, summaryRepository);

    @Test
    void recalculatesComplianceSummary() {
        var summary = new ComplianceSummary(10L);
        when(summaryRepository.findByProjectId(10L)).thenReturn(Optional.of(summary));
        when(summaryRepository.save(summary)).thenReturn(summary);
        when(dataProvider.countCompliantMeasurements(10L)).thenReturn(8);
        when(dataProvider.countNonCompliantMeasurements(10L)).thenReturn(2);

        var result = complianceService.recalculateCompliance(10L);

        assertEquals(10L, result.getProjectId());
        assertEquals(80.0, result.getCompliancePercentage());
        assertEquals(8, result.getCompliantMeasurements());
        assertEquals(2, result.getNonCompliantMeasurements());
        assertNotNull(result.getCalculatedAt());
    }

    @Test
    void updatesComplianceValues() {
        var summary = new ComplianceSummary(10L);

        summary.recalculate(8, 2);

        assertEquals(8, summary.getCompliantMeasurements());
        assertEquals(2, summary.getNonCompliantMeasurements());
        assertEquals(80.0, summary.getCompliancePercentage());
    }
}
