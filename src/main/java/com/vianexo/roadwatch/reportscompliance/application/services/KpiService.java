package com.vianexo.roadwatch.reportscompliance.application.services;

import com.vianexo.roadwatch.reportscompliance.application.outboundservices.ComplianceDataProvider;
import com.vianexo.roadwatch.reportscompliance.domain.model.entities.EnvironmentalKpi;
import com.vianexo.roadwatch.reportscompliance.domain.repositories.EnvironmentalKpiRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KpiService {

    private final EnvironmentalKpiRepository kpiRepository;
    private final ComplianceDataProvider dataProvider;

    public KpiService(EnvironmentalKpiRepository kpiRepository, ComplianceDataProvider dataProvider) {
        this.kpiRepository = kpiRepository;
        this.dataProvider = dataProvider;
    }

    public EnvironmentalKpi recalculateProjectKpis(Long projectId) {
        var kpi = kpiRepository.findByProjectId(projectId)
                .orElseGet(() -> new EnvironmentalKpi(projectId));
        var compliantMeasurements = dataProvider.countCompliantMeasurements(projectId);
        var nonCompliantMeasurements = dataProvider.countNonCompliantMeasurements(projectId);
        var totalMeasurements = compliantMeasurements + nonCompliantMeasurements;
        var compliancePercentage = totalMeasurements == 0 ? 0.0
                : compliantMeasurements * 100.0 / totalMeasurements;
        kpi.recalculate(compliancePercentage,
                dataProvider.averageResolutionHours(projectId),
                dataProvider.countOpenIncidents(projectId),
                dataProvider.countCriticalIncidents(projectId));
        return kpiRepository.save(kpi);
    }

    public List<EnvironmentalKpi> findAll() {
        return kpiRepository.findAll();
    }
}
