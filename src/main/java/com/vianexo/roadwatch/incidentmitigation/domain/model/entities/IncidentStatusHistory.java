package com.vianexo.roadwatch.incidentmitigation.domain.model.entities;

import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class IncidentStatusHistory {

    private Long id;
    private final Long incidentId;
    private final IncidentStatus previousStatus;
    private final IncidentStatus newStatus;
    private final Long changedByUserId;
    private LocalDateTime changedAt;

    public IncidentStatusHistory(Long incidentId, IncidentStatus previousStatus, IncidentStatus newStatus,
                                 Long changedByUserId) {
        if (incidentId == null || incidentId <= 0) {
            throw new IllegalArgumentException("Incident id must be positive");
        }
        if (changedByUserId == null || changedByUserId <= 0) {
            throw new IllegalArgumentException("Changed by user id must be positive");
        }
        this.incidentId = incidentId;
        this.previousStatus = previousStatus;
        this.newStatus = Objects.requireNonNull(newStatus, "New status is required");
        this.changedByUserId = changedByUserId;
        this.changedAt = LocalDateTime.now();
    }

    public static IncidentStatusHistory reconstitute(Long id, Long incidentId, IncidentStatus previousStatus,
                                                     IncidentStatus newStatus, Long changedByUserId,
                                                     LocalDateTime changedAt) {
        var history = new IncidentStatusHistory(incidentId, previousStatus, newStatus, changedByUserId);
        history.id = id;
        history.changedAt = changedAt;
        return history;
    }
}
