package com.vianexo.roadwatch.incidentmitigation.application.internal.policies;

import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.MarkOverdueIncidentsCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.services.EnvironmentalIncidentCommandService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OverdueCheckPolicy {

    private final EnvironmentalIncidentCommandService commandService;
    private final int maxOpenDays;
    private final Long changedByUserId;

    public OverdueCheckPolicy(EnvironmentalIncidentCommandService commandService,
                              @Value("${incident-mitigation.overdue-check.max-open-days}") int maxOpenDays,
                              @Value("${incident-mitigation.overdue-check.changed-by-user-id}") Long changedByUserId) {
        this.commandService = commandService;
        this.maxOpenDays = maxOpenDays;
        this.changedByUserId = changedByUserId;
    }

    @Scheduled(fixedDelayString = "${incident-mitigation.overdue-check.fixed-delay}")
    public void checkOverdueIncidents() {
        commandService.handle(new MarkOverdueIncidentsCommand(maxOpenDays, changedByUserId));
    }
}
