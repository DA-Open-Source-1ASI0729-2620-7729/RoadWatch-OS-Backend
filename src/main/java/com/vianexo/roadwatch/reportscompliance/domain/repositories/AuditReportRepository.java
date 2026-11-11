package com.vianexo.roadwatch.reportscompliance.domain.repositories;

import com.vianexo.roadwatch.reportscompliance.domain.model.aggregates.AuditReport;

import java.util.List;
import java.util.Optional;

public interface AuditReportRepository {

    AuditReport save(AuditReport report);

    List<AuditReport> findAll();

    Optional<AuditReport> findById(Long id);
}