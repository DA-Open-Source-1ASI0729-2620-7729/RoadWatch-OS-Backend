package com.vianexo.roadwatch.incidentmitigation.domain.repositories;

import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.MitigationAction;

import java.util.List;
import java.util.Optional;

public interface MitigationActionRepository {

    MitigationAction save(MitigationAction action);

    Optional<MitigationAction> findById(Long id);

    List<MitigationAction> findByIncidentId(Long incidentId);
}
