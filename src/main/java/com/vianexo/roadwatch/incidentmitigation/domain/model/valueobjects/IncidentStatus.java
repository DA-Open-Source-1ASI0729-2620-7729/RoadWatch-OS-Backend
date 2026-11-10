package com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects;

import java.util.Set;

public enum IncidentStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED,
    CLOSED,
    OVERDUE;

    public boolean canTransitionTo(IncidentStatus target) {
        return switch (this) {
            case OPEN -> Set.of(IN_PROGRESS, RESOLVED, OVERDUE).contains(target);
            case IN_PROGRESS -> Set.of(RESOLVED, OVERDUE).contains(target);
            case OVERDUE -> Set.of(IN_PROGRESS, RESOLVED).contains(target);
            case RESOLVED -> Set.of(IN_PROGRESS, CLOSED).contains(target);
            case CLOSED -> false;
        };
    }
}
