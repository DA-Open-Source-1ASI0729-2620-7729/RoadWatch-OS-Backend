package com.vianexo.roadwatch.reportscompliance.infrastructure.persistence.jpa.adapters;

import com.vianexo.roadwatch.reportscompliance.domain.model.entities.EnvironmentalKpi;
import com.vianexo.roadwatch.reportscompliance.domain.repositories.EnvironmentalKpiRepository;
import com.vianexo.roadwatch.reportscompliance.infrastructure.persistence.jpa.repositories.EnvironmentalKpiPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EnvironmentalKpiRepositoryImpl implements EnvironmentalKpiRepository {

    private final EnvironmentalKpiPersistenceRepository persistenceRepository;

    public EnvironmentalKpiRepositoryImpl(EnvironmentalKpiPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public EnvironmentalKpi save(EnvironmentalKpi kpi) {
        return persistenceRepository.save(kpi);
    }

    @Override
    public List<EnvironmentalKpi> findAll() {
        return persistenceRepository.findAll();
    }

    @Override
    public Optional<EnvironmentalKpi> findByProjectId(Long projectId) {
        return persistenceRepository.findFirstByProjectIdOrderByCalculatedAtDesc(projectId);
    }
}
