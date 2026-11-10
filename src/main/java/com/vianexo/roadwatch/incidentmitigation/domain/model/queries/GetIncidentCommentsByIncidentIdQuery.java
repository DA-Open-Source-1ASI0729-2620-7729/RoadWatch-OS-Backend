package com.vianexo.roadwatch.incidentmitigation.domain.model.queries;

public record GetIncidentCommentsByIncidentIdQuery(Long incidentId) {
    public GetIncidentCommentsByIncidentIdQuery {
        if (incidentId == null || incidentId <= 0) {
            throw new IllegalArgumentException("Incident id must be positive");
        }
    }
}
