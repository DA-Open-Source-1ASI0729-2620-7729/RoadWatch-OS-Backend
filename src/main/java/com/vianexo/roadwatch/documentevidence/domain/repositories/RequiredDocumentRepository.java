package com.vianexo.roadwatch.documentevidence.domain.repositories;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.RequiredDocument;

import java.util.List;

public interface RequiredDocumentRepository {

    RequiredDocument save(RequiredDocument document);

    List<RequiredDocument> findAll();
}
