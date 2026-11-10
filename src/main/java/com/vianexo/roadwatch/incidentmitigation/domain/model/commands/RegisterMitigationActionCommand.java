package com.vianexo.roadwatch.incidentmitigation.domain.model.commands;

public record RegisterMitigationActionCommand(Long incidentId, String description, Long responsibleUserId) {
    public RegisterMitigationActionCommand {
        if (incidentId == null || incidentId <= 0) {
            throw new IllegalArgumentException("Incident id must be positive");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Mitigation action description is required");
        }
    }
}
