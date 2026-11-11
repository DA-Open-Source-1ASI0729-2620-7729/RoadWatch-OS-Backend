package com.vianexo.roadwatch.incidentmitigation.domain.services;

import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.AddIncidentCommentCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentComment;

public interface IncidentCommentCommandService {

    IncidentComment handle(AddIncidentCommentCommand command);
}
