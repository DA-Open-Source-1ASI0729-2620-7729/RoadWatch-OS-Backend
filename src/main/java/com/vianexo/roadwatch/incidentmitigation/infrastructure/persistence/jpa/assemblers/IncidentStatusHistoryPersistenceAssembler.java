package com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.assemblers;

import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentStatusHistory;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.entities.EnvironmentalIncidentPersistenceEntity;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.entities.IncidentStatusHistoryPersistenceEntity;

public final class IncidentStatusHistoryPersistenceAssembler {

    private IncidentStatusHistoryPersistenceAssembler() {
    }

    public static IncidentStatusHistory toDomainFromPersistence(IncidentStatusHistoryPersistenceEntity entity) {
        return IncidentStatusHistory.reconstitute(entity.getId(), entity.getIncident().getId(),
                entity.getPreviousStatus(), entity.getNewStatus(), entity.getChangedByUserId(), entity.getChangedAt());
    }

    public static IncidentStatusHistoryPersistenceEntity toPersistenceFromDomain(
            IncidentStatusHistory history, EnvironmentalIncidentPersistenceEntity incident) {
        var entity = new IncidentStatusHistoryPersistenceEntity();
        entity.setIncident(incident);
        entity.setPreviousStatus(history.getPreviousStatus());
        entity.setNewStatus(history.getNewStatus());
        entity.setChangedByUserId(history.getChangedByUserId());
        entity.setChangedAt(history.getChangedAt());
        return entity;
    }
}
