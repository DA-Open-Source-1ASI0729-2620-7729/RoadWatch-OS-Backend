package com.vianexo.roadwatch.incidentmitigation.domain.services;

import com.vianexo.roadwatch.incidentmitigation.domain.model.aggregates.EnvironmentalIncident;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.ChangeIncidentSeverityCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.ChangeIncidentStatusCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.CreateEnvironmentalIncidentCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.MarkOverdueIncidentsCommand;

import java.util.List;

public interface EnvironmentalIncidentCommandService {

    EnvironmentalIncident handle(CreateEnvironmentalIncidentCommand command);

    EnvironmentalIncident handle(ChangeIncidentStatusCommand command);

    EnvironmentalIncident handle(ChangeIncidentSeverityCommand command);

    List<EnvironmentalIncident> handle(MarkOverdueIncidentsCommand command);
}
