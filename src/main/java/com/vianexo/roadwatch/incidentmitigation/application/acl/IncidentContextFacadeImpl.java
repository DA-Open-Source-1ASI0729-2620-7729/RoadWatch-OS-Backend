package com.vianexo.roadwatch.incidentmitigation.application.acl;

import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.CreateEnvironmentalIncidentCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetEnvironmentalIncidentByIdQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;
import com.vianexo.roadwatch.incidentmitigation.domain.services.EnvironmentalIncidentCommandService;
import com.vianexo.roadwatch.incidentmitigation.domain.services.EnvironmentalIncidentQueryService;
import com.vianexo.roadwatch.incidentmitigation.interfaces.acl.IncidentContextFacade;
import org.springframework.stereotype.Service;

@Service
public class IncidentContextFacadeImpl implements IncidentContextFacade {

    private final EnvironmentalIncidentCommandService commandService;
    private final EnvironmentalIncidentQueryService queryService;

    public IncidentContextFacadeImpl(EnvironmentalIncidentCommandService commandService,
                                     EnvironmentalIncidentQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Override
    public Long createIncidentFromAlert(Long measurementId, String title, String description, String severity) {
        var command = new CreateEnvironmentalIncidentCommand(measurementId, title, description,
                IncidentSeverity.valueOf(severity));
        return commandService.handle(command).getId();
    }

    @Override
    public boolean existsIncidentById(Long incidentId) {
        if (incidentId == null || incidentId <= 0) {
            return false;
        }
        return queryService.handle(new GetEnvironmentalIncidentByIdQuery(incidentId)).isPresent();
    }
}
