package com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.assemblers;

import com.vianexo.roadwatch.incidentmitigation.domain.model.aggregates.EnvironmentalIncident;
import com.vianexo.roadwatch.incidentmitigation.infrastructure.persistence.jpa.entities.EnvironmentalIncidentPersistenceEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public final class EnvironmentalIncidentPersistenceAssembler {

    private EnvironmentalIncidentPersistenceAssembler() {
    }

    public static EnvironmentalIncident toDomainFromPersistence(EnvironmentalIncidentPersistenceEntity entity) {
        return EnvironmentalIncident.reconstitute(entity.getId(), entity.getMeasurementId(), entity.getTitle(),
                entity.getDescription(), entity.getStatus(), entity.getSeverity(),
                toLocalDateTime(entity.getCreatedAt()), toLocalDateTime(entity.getUpdatedAt()));
    }

    public static EnvironmentalIncidentPersistenceEntity toPersistenceFromDomain(
            EnvironmentalIncident incident, EnvironmentalIncidentPersistenceEntity entity) {
        entity.setMeasurementId(incident.getMeasurementId());
        entity.setTitle(incident.getTitle());
        entity.setDescription(incident.getDescription());
        entity.setStatus(incident.getStatus());
        entity.setSeverity(incident.getSeverity());
        return entity;
    }

    public static Date toDate(LocalDateTime value) {
        return Date.from(value.atZone(ZoneId.systemDefault()).toInstant());
    }

    private static LocalDateTime toLocalDateTime(Date value) {
        return value == null ? null : LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault());
    }
}
