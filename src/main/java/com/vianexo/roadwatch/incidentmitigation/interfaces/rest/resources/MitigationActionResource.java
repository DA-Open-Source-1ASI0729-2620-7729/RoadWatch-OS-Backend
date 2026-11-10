package com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources;

import java.time.LocalDateTime;

public record MitigationActionResource(
        Long id,
        Long incidentId,
        String description,
        Long responsibleUserId,
        boolean completed,
        LocalDateTime createdAt,
        LocalDateTime completedAt
) {
}
