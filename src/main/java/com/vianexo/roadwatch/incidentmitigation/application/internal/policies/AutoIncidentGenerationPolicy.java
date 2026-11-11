package com.vianexo.roadwatch.incidentmitigation.application.internal.policies;

import com.vianexo.roadwatch.incidentmitigation.application.events.EnvironmentalAlertGeneratedEvent;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.CreateEnvironmentalIncidentCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;
import com.vianexo.roadwatch.incidentmitigation.domain.services.EnvironmentalIncidentCommandService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AutoIncidentGenerationPolicy {

    private final EnvironmentalIncidentCommandService commandService;

    public AutoIncidentGenerationPolicy(EnvironmentalIncidentCommandService commandService) {
        this.commandService = commandService;
    }

    @EventListener
    public void createIncident(EnvironmentalAlertGeneratedEvent event) {
        commandService.handle(new CreateEnvironmentalIncidentCommand(event.measurementId(), event.title(),
                event.description(), IncidentSeverity.valueOf(event.severity())));
    }
}
