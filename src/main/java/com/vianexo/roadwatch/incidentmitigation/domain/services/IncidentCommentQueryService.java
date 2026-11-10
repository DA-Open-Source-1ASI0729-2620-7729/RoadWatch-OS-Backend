package com.vianexo.roadwatch.incidentmitigation.domain.services;

import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentComment;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetIncidentCommentsByIncidentIdQuery;

import java.util.List;

public interface IncidentCommentQueryService {

    List<IncidentComment> handle(GetIncidentCommentsByIncidentIdQuery query);
}
