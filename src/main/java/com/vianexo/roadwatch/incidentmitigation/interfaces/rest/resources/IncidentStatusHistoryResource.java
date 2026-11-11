package com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources;

import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentStatus;

import java.time.LocalDateTime;

public record IncidentStatusHistoryResource(
        Long id,
        Long incidentId,
        IncidentStatus previousStatus,
        IncidentStatus newStatus,
        Long changedByUserId,
        LocalDateTime changedAt
) {
}
