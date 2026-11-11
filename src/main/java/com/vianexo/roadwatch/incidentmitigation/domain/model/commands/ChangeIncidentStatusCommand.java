package com.vianexo.roadwatch.incidentmitigation.domain.model.commands;

import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentStatus;

public record ChangeIncidentStatusCommand(Long incidentId, IncidentStatus status, Long changedByUserId) {
    public ChangeIncidentStatusCommand {
        if (incidentId == null || incidentId <= 0) {
            throw new IllegalArgumentException("Incident id must be positive");
        }
        if (status == null) {
            throw new IllegalArgumentException("Incident status is required");
        }
        if (changedByUserId == null || changedByUserId <= 0) {
            throw new IllegalArgumentException("Changed by user id must be positive");
        }
    }
}
