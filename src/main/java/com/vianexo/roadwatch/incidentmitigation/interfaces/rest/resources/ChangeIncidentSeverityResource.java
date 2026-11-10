package com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources;

import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;
import jakarta.validation.constraints.NotNull;

public record ChangeIncidentSeverityResource(@NotNull IncidentSeverity severity) {
}
