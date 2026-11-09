package com.vianexo.roadwatch.documentevidence.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.RequiredDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequiredDocumentPersistenceRepository extends JpaRepository<RequiredDocument, Long> {
}
