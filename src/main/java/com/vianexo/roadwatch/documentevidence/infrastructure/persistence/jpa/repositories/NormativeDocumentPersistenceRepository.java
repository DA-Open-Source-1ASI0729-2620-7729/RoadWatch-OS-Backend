package com.vianexo.roadwatch.documentevidence.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.NormativeDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NormativeDocumentPersistenceRepository  extends JpaRepository<NormativeDocument, Long> {
}
