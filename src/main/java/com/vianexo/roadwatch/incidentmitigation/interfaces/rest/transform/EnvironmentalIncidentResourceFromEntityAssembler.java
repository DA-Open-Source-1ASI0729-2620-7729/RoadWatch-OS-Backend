package com.vianexo.roadwatch.incidentmitigation.interfaces.rest.transform;

import com.vianexo.roadwatch.incidentmitigation.domain.model.aggregates.EnvironmentalIncident;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources.EnvironmentalIncidentResource;

public final class EnvironmentalIncidentResourceFromEntityAssembler {

    private EnvironmentalIncidentResourceFromEntityAssembler() {
    }

    public static EnvironmentalIncidentResource toResourceFromEntity(EnvironmentalIncident incident) {
        return new EnvironmentalIncidentResource(incident.getId(), incident.getMeasurementId(), incident.getTitle(),
                incident.getDescription(), incident.getStatus(), incident.getSeverity(), incident.getCreatedAt(),
                incident.getUpdatedAt());
    }
}
