package com.vianexo.roadwatch.incidentmitigation.interfaces.rest.transform;

import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.MitigationAction;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources.MitigationActionResource;

public final class MitigationActionResourceFromEntityAssembler {

    private MitigationActionResourceFromEntityAssembler() {
    }

    public static MitigationActionResource toResourceFromEntity(MitigationAction action) {
        return new MitigationActionResource(action.getId(), action.getIncidentId(), action.getDescription(),
                action.getResponsibleUserId(), action.isCompleted(), action.getCreatedAt(), action.getCompletedAt());
    }
}
