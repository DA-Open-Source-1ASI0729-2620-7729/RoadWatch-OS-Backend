package com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources;

import java.time.LocalDateTime;

public record IncidentCommentResource(
        Long id,
        Long incidentId,
        Long authorUserId,
        String content,
        LocalDateTime createdAt
) {
}
