package com.vianexo.roadwatch.reportscompliance.application.internal.policies;

import com.vianexo.roadwatch.documentevidence.application.events.DocumentVersionUploadedEvent;
import com.vianexo.roadwatch.reportscompliance.application.services.ComplianceService;
import com.vianexo.roadwatch.reportscompliance.application.services.KpiService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ReportingEventHandler {

    private final KpiService kpiService;
    private final ComplianceService complianceService;

    public ReportingEventHandler(KpiService kpiService, ComplianceService complianceService) {
        this.kpiService = kpiService;
        this.complianceService = complianceService;
    }

    @EventListener
    public void recalculateProjections(DocumentVersionUploadedEvent event) {
        kpiService.recalculateProjectKpis(event.projectId());
        complianceService.recalculateCompliance(event.projectId());
    }
}
