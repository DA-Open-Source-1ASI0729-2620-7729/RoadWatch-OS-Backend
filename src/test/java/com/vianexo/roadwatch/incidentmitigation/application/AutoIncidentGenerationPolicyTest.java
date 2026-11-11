package com.vianexo.roadwatch.incidentmitigation.application;

import com.vianexo.roadwatch.incidentmitigation.application.events.EnvironmentalAlertGeneratedEvent;
import com.vianexo.roadwatch.incidentmitigation.application.internal.policies.AutoIncidentGenerationPolicy;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.CreateEnvironmentalIncidentCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;
import com.vianexo.roadwatch.incidentmitigation.domain.services.EnvironmentalIncidentCommandService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AutoIncidentGenerationPolicyTest {

    @Test
    void policyCreatesAnIncidentFromAnEnvironmentalAlert() {
        var commandService = mock(EnvironmentalIncidentCommandService.class);
        var policy = new AutoIncidentGenerationPolicy(commandService);
        var event = new EnvironmentalAlertGeneratedEvent(10L, "Noise alert", "Noise exceeded the limit", "HIGH");

        policy.createIncident(event);

        verify(commandService).handle(new CreateEnvironmentalIncidentCommand(10L, "Noise alert",
                "Noise exceeded the limit", IncidentSeverity.HIGH));
    }
}
