package com.vianexo.roadwatch.reportscompliance.application.services;

import com.vianexo.roadwatch.reportscompliance.application.outboundservices.ComplianceDataProvider;
import com.vianexo.roadwatch.reportscompliance.domain.model.entities.EnvironmentalKpi;
import com.vianexo.roadwatch.reportscompliance.domain.repositories.EnvironmentalKpiRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class KpiServiceTest {

    private final EnvironmentalKpiRepository kpiRepository = mock(EnvironmentalKpiRepository.class);
    private final ComplianceDataProvider dataProvider = mock(ComplianceDataProvider.class);
    private final KpiService kpiService = new KpiService(kpiRepository, dataProvider);

    @Test
    void recalculatesProjectKpis() {
        var kpi = new EnvironmentalKpi(10L);
        when(kpiRepository.findByProjectId(10L)).thenReturn(Optional.of(kpi));
        when(kpiRepository.save(kpi)).thenReturn(kpi);
        when(dataProvider.countCompliantMeasurements(10L)).thenReturn(8);
        when(dataProvider.countNonCompliantMeasurements(10L)).thenReturn(2);
        when(dataProvider.averageResolutionHours(10L)).thenReturn(12.5);
        when(dataProvider.countOpenIncidents(10L)).thenReturn(3);
        when(dataProvider.countCriticalIncidents(10L)).thenReturn(1);

        var result = kpiService.recalculateProjectKpis(10L);

        assertEquals(10L, result.getProjectId());
        assertEquals(80.0, result.getCompliancePercentage());
        assertEquals(12.5, result.getAverageResolutionTime());
        assertEquals(3, result.getOpenIncidents());
        assertEquals(1, result.getCriticalIncidents());
        assertNotNull(result.getCalculatedAt());
    }

    @Test
    void updatesKpiValues() {
        var kpi = new EnvironmentalKpi(10L);

        kpi.recalculate(85.0, 12.5, 3, 1);

        assertEquals(85.0, kpi.getCompliancePercentage());
        assertEquals(12.5, kpi.getAverageResolutionTime());
        assertEquals(3, kpi.getOpenIncidents());
        assertEquals(1, kpi.getCriticalIncidents());
    }
}
