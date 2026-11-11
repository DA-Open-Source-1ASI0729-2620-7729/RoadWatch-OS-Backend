package com.vianexo.roadwatch.reportscompliance.infrastructure.persistence.jpa.adapters;

import com.vianexo.roadwatch.reportscompliance.domain.model.aggregates.AuditReport;
import com.vianexo.roadwatch.reportscompliance.domain.repositories.AuditReportRepository;
import com.vianexo.roadwatch.reportscompliance.infrastructure.persistence.jpa.repositories.AuditReportPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuditReportRepositoryImpl implements AuditReportRepository {

    private final AuditReportPersistenceRepository persistenceRepository;

    public AuditReportRepositoryImpl(AuditReportPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public AuditReport save(AuditReport report) {
        return persistenceRepository.save(report);
    }

    @Override
    public List<AuditReport> findAll() {
        return persistenceRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public Optional<AuditReport> findById(Long id) {
        return persistenceRepository.findById(id);
    }
}