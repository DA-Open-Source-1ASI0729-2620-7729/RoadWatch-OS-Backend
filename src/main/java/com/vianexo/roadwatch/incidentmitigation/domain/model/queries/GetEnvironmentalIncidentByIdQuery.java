package com.vianexo.roadwatch.incidentmitigation.domain.model.queries;

public record GetEnvironmentalIncidentByIdQuery(Long incidentId) {
    public GetEnvironmentalIncidentByIdQuery {
        if (incidentId == null || incidentId <= 0) {
            throw new IllegalArgumentException("Incident id must be positive");
        }
    }
}
