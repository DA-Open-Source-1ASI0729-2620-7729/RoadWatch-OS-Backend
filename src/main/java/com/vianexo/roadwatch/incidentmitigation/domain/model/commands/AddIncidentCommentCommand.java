package com.vianexo.roadwatch.incidentmitigation.domain.model.commands;

public record AddIncidentCommentCommand(Long incidentId, Long authorUserId, String content) {
    public AddIncidentCommentCommand {
        if (incidentId == null || incidentId <= 0) {
            throw new IllegalArgumentException("Incident id must be positive");
        }
        if (authorUserId == null || authorUserId <= 0) {
            throw new IllegalArgumentException("Author user id must be positive");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Comment content is required");
        }
    }
}
