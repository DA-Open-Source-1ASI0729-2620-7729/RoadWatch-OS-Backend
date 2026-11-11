package com.vianexo.roadwatch.incidentmitigation.domain.services;

import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.MitigationAction;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetMitigationActionsByIncidentIdQuery;

import java.util.List;

public interface MitigationActionQueryService {

    List<MitigationAction> handle(GetMitigationActionsByIncidentIdQuery query);
}
