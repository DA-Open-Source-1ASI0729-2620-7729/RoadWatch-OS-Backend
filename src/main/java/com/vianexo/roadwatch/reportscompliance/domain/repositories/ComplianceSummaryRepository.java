package com.vianexo.roadwatch.reportscompliance.domain.repositories;

import com.vianexo.roadwatch.reportscompliance.domain.model.entities.ComplianceSummary;

import java.util.List;
import java.util.Optional;

public interface ComplianceSummaryRepository {

    ComplianceSummary save(ComplianceSummary summary);

    List<ComplianceSummary> findAll();

    Optional<ComplianceSummary> findByProjectId(Long projectId);
}
