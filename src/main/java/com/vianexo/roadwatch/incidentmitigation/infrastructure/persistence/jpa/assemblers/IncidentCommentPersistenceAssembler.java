package com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.assemblers;

import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentComment;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.entities.EnvironmentalIncidentPersistenceEntity;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.entities.IncidentCommentPersistenceEntity;

public final class IncidentCommentPersistenceAssembler {

    private IncidentCommentPersistenceAssembler() {
    }

    public static IncidentComment toDomainFromPersistence(IncidentCommentPersistenceEntity entity) {
        return IncidentComment.reconstitute(entity.getId(), entity.getIncident().getId(), entity.getAuthorUserId(),
                entity.getContent(), entity.getCreatedAt());
    }

    public static IncidentCommentPersistenceEntity toPersistenceFromDomain(
            IncidentComment comment, EnvironmentalIncidentPersistenceEntity incident) {
        var entity = new IncidentCommentPersistenceEntity();
        entity.setIncident(incident);
        entity.setAuthorUserId(comment.getAuthorUserId());
        entity.setContent(comment.getContent());
        entity.setCreatedAt(comment.getCreatedAt());
        return entity;
    }
}
