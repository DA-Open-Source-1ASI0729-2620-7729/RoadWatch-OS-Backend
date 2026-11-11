package com.vianexo.roadwatch.incidentmitigation.interfaces.rest.transform;

import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.CreateEnvironmentalIncidentCommand;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources.CreateEnvironmentalIncidentResource;

public final class CreateEnvironmentalIncidentCommandFromResourceAssembler {

    private CreateEnvironmentalIncidentCommandFromResourceAssembler() {
    }

    public static CreateEnvironmentalIncidentCommand toCommandFromResource(CreateEnvironmentalIncidentResource resource) {
        return new CreateEnvironmentalIncidentCommand(resource.measurementId(), resource.title(),
                resource.description(), resource.severity());
    }
}
