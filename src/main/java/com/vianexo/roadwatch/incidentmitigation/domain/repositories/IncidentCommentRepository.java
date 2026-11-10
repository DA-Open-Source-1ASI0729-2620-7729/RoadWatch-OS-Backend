package com.vianexo.roadwatch.incidentmitigation.domain.repositories;

import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentComment;

import java.util.List;

public interface IncidentCommentRepository {

    IncidentComment save(IncidentComment comment);

    List<IncidentComment> findByIncidentId(Long incidentId);
}
