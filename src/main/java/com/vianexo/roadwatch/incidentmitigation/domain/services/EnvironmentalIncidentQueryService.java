package com.vianexo.roadwatch.incidentmitigation.domain.services;

import com.vianexo.roadwatch.incidentmitigation.domain.model.aggregates.EnvironmentalIncident;
import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentStatusHistory;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetAllEnvironmentalIncidentsQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetCriticalIncidentsQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetEnvironmentalIncidentByIdQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetIncidentStatusHistoryQuery;

import java.util.List;
import java.util.Optional;

public interface EnvironmentalIncidentQueryService {

    Optional<EnvironmentalIncident> handle(GetEnvironmentalIncidentByIdQuery query);

    List<EnvironmentalIncident> handle(GetAllEnvironmentalIncidentsQuery query);

    List<EnvironmentalIncident> handle(GetCriticalIncidentsQuery query);

    List<IncidentStatusHistory> handle(GetIncidentStatusHistoryQuery query);
}
