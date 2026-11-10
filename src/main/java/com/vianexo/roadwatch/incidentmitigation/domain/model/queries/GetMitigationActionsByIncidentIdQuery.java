package com.vianexo.roadwatch.incidentmitigation.domain.model.queries;

public record GetMitigationActionsByIncidentIdQuery(Long incidentId) {
    public GetMitigationActionsByIncidentIdQuery {
        if (incidentId == null || incidentId <= 0) {
            throw new IllegalArgumentException("Incident id must be positive");
        }
    }
}
