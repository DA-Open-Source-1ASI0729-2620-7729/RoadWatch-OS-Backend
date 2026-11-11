package com.vianexo.roadwatch.incidentmitigation.domain.model.commands;

public record MarkOverdueIncidentsCommand(int maxOpenDays, Long changedByUserId) {
    public MarkOverdueIncidentsCommand {
        if (maxOpenDays < 1) {
            throw new IllegalArgumentException("Max open days must be at least 1");
        }
        if (changedByUserId == null || changedByUserId <= 0) {
            throw new IllegalArgumentException("Changed by user id must be positive");
        }
    }
}
