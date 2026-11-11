package com.vianexo.roadwatch.reportscompliance.application.services;

import com.vianexo.roadwatch.reportscompliance.domain.model.entities.EnvironmentalKpi;
import com.vianexo.roadwatch.reportscompliance.domain.repositories.EnvironmentalKpiRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KpiService {

    private final EnvironmentalKpiRepository kpiRepository;

    public KpiService(EnvironmentalKpiRepository kpiRepository) {
        this.kpiRepository = kpiRepository;
    }

    public EnvironmentalKpi recalculateProjectKpis(Long projectId) {
        var kpi = kpiRepository.findByProjectId(projectId)
                .orElseGet(() -> new EnvironmentalKpi(projectId));
        kpi.recalculate(0.0, null, 0, 0);
        return kpiRepository.save(kpi);
    }

    public List<EnvironmentalKpi> findAll() {
        return kpiRepository.findAll();
    }
}
