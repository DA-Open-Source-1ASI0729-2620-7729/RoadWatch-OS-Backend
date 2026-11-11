package com.vianexo.roadwatch.reportscompliance.application.internal.policies;

import com.vianexo.roadwatch.documentevidence.application.events.DocumentVersionUploadedEvent;
import com.vianexo.roadwatch.reportscompliance.application.services.ComplianceService;
import com.vianexo.roadwatch.reportscompliance.application.services.KpiService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ReportingEventHandlerTest {

    @Test
    void recalculatesProjectProjectionsWhenADocumentVersionIsUploaded() {
        var kpiService = mock(KpiService.class);
        var complianceService = mock(ComplianceService.class);
        var handler = new ReportingEventHandler(kpiService, complianceService);

        handler.recalculateProjections(new DocumentVersionUploadedEvent(10L));

        verify(kpiService).recalculateProjectKpis(10L);
        verify(complianceService).recalculateCompliance(10L);
    }
}
