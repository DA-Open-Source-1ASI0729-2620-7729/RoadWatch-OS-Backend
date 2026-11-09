package com.vianexo.roadwatch.documentevidence.domain.repositories;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.Evidence;

import java.util.List;
import java.util.Optional;

public interface EvidenceRepository {

    Evidence save(Evidence evidence);

    List<Evidence> findAll();

    Optional<Evidence> findById(Long id);
}
