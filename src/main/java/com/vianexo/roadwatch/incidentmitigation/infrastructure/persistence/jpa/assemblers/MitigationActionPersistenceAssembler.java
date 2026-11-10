package com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.assemblers;

import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.MitigationAction;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.entities.EnvironmentalIncidentPersistenceEntity;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.entities.MitigationActionPersistenceEntity;

public final class MitigationActionPersistenceAssembler {

    private MitigationActionPersistenceAssembler() {
    }

    public static MitigationAction toDomainFromPersistence(MitigationActionPersistenceEntity entity) {
        return MitigationAction.reconstitute(entity.getId(), entity.getIncident().getId(), entity.getDescription(),
                entity.getResponsibleUserId(), entity.getCreatedAt(), entity.getCompletedAt());
    }

    public static MitigationActionPersistenceEntity toPersistenceFromDomain(
            MitigationAction action, EnvironmentalIncidentPersistenceEntity incident,
            MitigationActionPersistenceEntity entity) {
        entity.setIncident(incident);
        entity.setDescription(action.getDescription());
        entity.setResponsibleUserId(action.getResponsibleUserId());
        entity.setCreatedAt(action.getCreatedAt());
        entity.setCompletedAt(action.getCompletedAt());
        return entity;
    }
}
