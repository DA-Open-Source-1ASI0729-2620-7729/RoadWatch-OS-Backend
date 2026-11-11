package com.vianexo.roadwatch.incidentmitigation.domain.model.commands;

import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;

public record ChangeIncidentSeverityCommand(Long incidentId, IncidentSeverity severity) {
    public ChangeIncidentSeverityCommand {
        if (incidentId == null || incidentId <= 0) {
            throw new IllegalArgumentException("Incident id must be positive");
        }
        if (severity == null) {
            throw new IllegalArgumentException("Incident severity is required");
        }
    }
}
