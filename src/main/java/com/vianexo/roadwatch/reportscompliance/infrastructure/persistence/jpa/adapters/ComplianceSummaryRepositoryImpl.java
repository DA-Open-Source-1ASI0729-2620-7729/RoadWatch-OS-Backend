package com.vianexo.roadwatch.reportscompliance.infrastructure.persistence.jpa.adapters;

import com.vianexo.roadwatch.reportscompliance.domain.model.entities.ComplianceSummary;
import com.vianexo.roadwatch.reportscompliance.domain.repositories.ComplianceSummaryRepository;
import com.vianexo.roadwatch.reportscompliance.infrastructure.persistence.jpa.repositories.ComplianceSummaryPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ComplianceSummaryRepositoryImpl implements ComplianceSummaryRepository {

    private final ComplianceSummaryPersistenceRepository persistenceRepository;

    public ComplianceSummaryRepositoryImpl(ComplianceSummaryPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public ComplianceSummary save(ComplianceSummary summary) {
        return persistenceRepository.save(summary);
    }

    @Override
    public List<ComplianceSummary> findAll() {
        return persistenceRepository.findAll();
    }

    @Override
    public Optional<ComplianceSummary> findByProjectId(Long projectId) {
        return persistenceRepository.findFirstByProjectIdOrderByCalculatedAtDesc(projectId);
    }
}
