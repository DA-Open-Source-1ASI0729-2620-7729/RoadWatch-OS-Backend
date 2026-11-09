package com.vianexo.roadwatch.documentevidence.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.Evidence;
import com.vianexo.roadwatch.documentevidence.domain.model.entities.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvidencePersistenceRepository extends JpaRepository<Evidence, Long> {
}



