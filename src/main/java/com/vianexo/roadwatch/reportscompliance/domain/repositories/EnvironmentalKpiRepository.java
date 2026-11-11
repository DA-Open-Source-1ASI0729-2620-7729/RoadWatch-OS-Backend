package com.vianexo.roadwatch.reportscompliance.domain.repositories;

import com.vianexo.roadwatch.reportscompliance.domain.model.entities.EnvironmentalKpi;

import java.util.List;
import java.util.Optional;

public interface EnvironmentalKpiRepository {

    EnvironmentalKpi save(EnvironmentalKpi kpi);

    List<EnvironmentalKpi> findAll();

    Optional<EnvironmentalKpi> findByProjectId(Long projectId);
}
