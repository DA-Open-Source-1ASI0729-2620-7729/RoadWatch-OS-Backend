package com.vianexo.roadwatch.documentevidence.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.RequiredDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequiredDocumentPersistenceRepository extends JpaRepository<RequiredDocument, Long> {

    List<RequiredDocument> findByProjectIdAndMilestoneName(Long projectId, String milestoneName);
}
