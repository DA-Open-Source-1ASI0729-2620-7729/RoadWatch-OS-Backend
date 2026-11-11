package com.vianexo.roadwatch.reportscompliance.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.reportscompliance.domain.model.entities.ComplianceSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComplianceSummaryPersistenceRepository extends JpaRepository<ComplianceSummary, Long> {

    Optional<ComplianceSummary> findFirstByProjectIdOrderByCalculatedAtDesc(Long projectId);
}
