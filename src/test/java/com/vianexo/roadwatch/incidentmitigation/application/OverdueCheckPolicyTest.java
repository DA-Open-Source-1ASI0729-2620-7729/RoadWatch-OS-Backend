package com.vianexo.roadwatch.incidentmitigation.application;

import com.vianexo.roadwatch.incidentmitigation.application.internal.policies.OverdueCheckPolicy;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.MarkOverdueIncidentsCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.services.EnvironmentalIncidentCommandService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class OverdueCheckPolicyTest {

    @Test
    void policyDelegatesTheOverdueCheckToTheCommandService() {
        var commandService = mock(EnvironmentalIncidentCommandService.class);
        var policy = new OverdueCheckPolicy(commandService, 7, 1L);

        policy.checkOverdueIncidents();

        verify(commandService).handle(new MarkOverdueIncidentsCommand(7, 1L));
    }
}
