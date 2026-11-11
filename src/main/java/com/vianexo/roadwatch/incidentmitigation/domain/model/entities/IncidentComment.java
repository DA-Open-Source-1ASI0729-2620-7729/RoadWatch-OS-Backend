package com.vianexo.roadwatch.incidentmitigation.domain.model.entities;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class IncidentComment {

    private Long id;
    private final Long incidentId;
    private final Long authorUserId;
    private final String content;
    private LocalDateTime createdAt;

    public IncidentComment(Long incidentId, Long authorUserId, String content) {
        if (incidentId == null || incidentId <= 0) {
            throw new IllegalArgumentException("Incident id must be positive");
        }
        if (authorUserId == null || authorUserId <= 0) {
            throw new IllegalArgumentException("Author user id must be positive");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Comment content is required");
        }
        this.incidentId = incidentId;
        this.authorUserId = authorUserId;
        this.content = content.trim();
        this.createdAt = LocalDateTime.now();
    }

    public static IncidentComment reconstitute(Long id, Long incidentId, Long authorUserId, String content,
                                               LocalDateTime createdAt) {
        var comment = new IncidentComment(incidentId, authorUserId, content);
        comment.id = id;
        comment.createdAt = createdAt;
        return comment;
    }
}
