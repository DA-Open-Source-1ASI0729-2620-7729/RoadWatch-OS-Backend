package com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources;

import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentStatus;

import java.time.LocalDateTime;

public record EnvironmentalIncidentResource(
        Long id,
        Long measurementId,
        String title,
        String description,
        IncidentStatus status,
        IncidentSeverity severity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
