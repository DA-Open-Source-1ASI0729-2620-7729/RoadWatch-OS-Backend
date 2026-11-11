package com.vianexo.roadwatch.incidentmitigation.domain.model.queries;

import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentStatus;

public record GetAllEnvironmentalIncidentsQuery(IncidentStatus status, IncidentSeverity severity) {
}
