package com.vianexo.roadwatch.incidentmitigation.interfaces.rest.transform;

import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentComment;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources.IncidentCommentResource;

public final class IncidentCommentResourceFromEntityAssembler {

    private IncidentCommentResourceFromEntityAssembler() {
    }

    public static IncidentCommentResource toResourceFromEntity(IncidentComment comment) {
        return new IncidentCommentResource(comment.getId(), comment.getIncidentId(), comment.getAuthorUserId(),
                comment.getContent(), comment.getCreatedAt());
    }
}
