package com.vianexo.roadwatch.documentevidence.domain.repositories;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.NormativeDocument;

import java.util.List;
import java.util.Optional;

public interface NormativeDocumentRepository {

    NormativeDocument save(NormativeDocument document);

    List<NormativeDocument> findAll();

    Optional<NormativeDocument> findById(Long id);
}
