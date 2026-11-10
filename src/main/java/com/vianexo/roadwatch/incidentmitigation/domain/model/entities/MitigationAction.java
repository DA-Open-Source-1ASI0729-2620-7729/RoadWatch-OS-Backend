package com.vianexo.roadwatch.incidentmitigation.domain.model.entities;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MitigationAction {

    private Long id;
    private final Long incidentId;
    private final String description;
    private final Long responsibleUserId;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    public MitigationAction(Long incidentId, String description, Long responsibleUserId) {
        if (incidentId == null || incidentId <= 0) {
            throw new IllegalArgumentException("Incident id must be positive");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Mitigation action description is required");
        }
        if (responsibleUserId != null && responsibleUserId <= 0) {
            throw new IllegalArgumentException("Responsible user id must be positive");
        }
        this.incidentId = incidentId;
        this.description = description.trim();
        this.responsibleUserId = responsibleUserId;
        this.createdAt = LocalDateTime.now();
    }

    public static MitigationAction reconstitute(Long id, Long incidentId, String description, Long responsibleUserId,
                                                LocalDateTime createdAt, LocalDateTime completedAt) {
        var action = new MitigationAction(incidentId, description, responsibleUserId);
        action.id = id;
        action.createdAt = createdAt;
        action.completedAt = completedAt;
        return action;
    }

    public void complete() {
        if (isCompleted()) {
            throw new IllegalStateException("Mitigation action is already completed");
        }
        this.completedAt = LocalDateTime.now();
    }

    public boolean isCompleted() {
        return completedAt != null;
    }
}
