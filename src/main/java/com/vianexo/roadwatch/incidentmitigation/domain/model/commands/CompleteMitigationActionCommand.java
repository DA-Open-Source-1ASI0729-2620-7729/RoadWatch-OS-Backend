package com.vianexo.roadwatch.incidentmitigation.domain.model.commands;

public record CompleteMitigationActionCommand(Long incidentId, Long mitigationActionId) {
    public CompleteMitigationActionCommand {
        if (incidentId == null || incidentId <= 0) {
            throw new IllegalArgumentException("Incident id must be positive");
        }
        if (mitigationActionId == null || mitigationActionId <= 0) {
            throw new IllegalArgumentException("Mitigation action id must be positive");
        }
    }
}
