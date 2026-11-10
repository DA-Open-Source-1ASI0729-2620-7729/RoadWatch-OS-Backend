package com.vianexo.roadwatch.reportscompliance.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.reportscompliance.domain.model.aggregates.AuditReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditReportPersistenceRepository extends JpaRepository<AuditReport, Long> {

    List<AuditReport> findAllByOrderByCreatedAtDesc();
}