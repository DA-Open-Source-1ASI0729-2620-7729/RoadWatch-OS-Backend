package com.vianexo.roadwatch.incidentmitigation.domain.model.queries;

public record GetIncidentStatusHistoryQuery(Long incidentId) {
    public GetIncidentStatusHistoryQuery {
        if (incidentId == null || incidentId <= 0) {
            throw new IllegalArgumentException("Incident id must be positive");
        }
    }
}
