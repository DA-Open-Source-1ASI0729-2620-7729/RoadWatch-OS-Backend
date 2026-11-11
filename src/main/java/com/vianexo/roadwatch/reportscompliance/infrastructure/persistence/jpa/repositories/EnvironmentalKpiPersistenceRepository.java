package com.vianexo.roadwatch.reportscompliance.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.reportscompliance.domain.model.entities.EnvironmentalKpi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnvironmentalKpiPersistenceRepository extends JpaRepository<EnvironmentalKpi, Long> {

    Optional<EnvironmentalKpi> findFirstByProjectIdOrderByCalculatedAtDesc(Long projectId);
}
