package com.vianexo.roadwatch.incidentmitigation.domain.model.commands;

import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;

public record CreateEnvironmentalIncidentCommand(Long measurementId, String title, String description,
                                                 IncidentSeverity severity) {
    public CreateEnvironmentalIncidentCommand {
        if (measurementId == null || measurementId <= 0) {
            throw new IllegalArgumentException("Measurement id must be positive");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Incident title is required");
        }
        if (severity == null) {
            throw new IllegalArgumentException("Incident severity is required");
        }
    }
}
