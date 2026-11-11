package com.vianexo.roadwatch.documentevidence.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.NormativeDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NormativeDocumentPersistenceRepository  extends JpaRepository<NormativeDocument, Long> {

    List<NormativeDocument> findByProjectId(Long projectId);
}
