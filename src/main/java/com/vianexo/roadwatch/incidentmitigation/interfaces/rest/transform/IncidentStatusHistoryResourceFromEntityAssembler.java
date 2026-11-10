package com.vianexo.roadwatch.incidentmitigation.interfaces.rest.transform;

import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentStatusHistory;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources.IncidentStatusHistoryResource;

public final class IncidentStatusHistoryResourceFromEntityAssembler {

    private IncidentStatusHistoryResourceFromEntityAssembler() {
    }

    public static IncidentStatusHistoryResource toResourceFromEntity(IncidentStatusHistory history) {
        return new IncidentStatusHistoryResource(history.getId(), history.getIncidentId(), history.getPreviousStatus(),
                history.getNewStatus(), history.getChangedByUserId(), history.getChangedAt());
    }
}
