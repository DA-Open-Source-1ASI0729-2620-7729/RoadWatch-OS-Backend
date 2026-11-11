package com.vianexo.roadwatch.reportscompliance.infrastructure.providers;

import com.vianexo.roadwatch.environmentalmonitoring.interfaces.acl.EnvironmentalMonitoringContextFacade;
import com.vianexo.roadwatch.incidentmitigation.interfaces.acl.IncidentContextFacade;
import com.vianexo.roadwatch.projectmanagement.interfaces.acl.ProjectContextFacade;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IntegratedComplianceDataProviderTest {

    @Test
    void obtainsProjectDataThroughBoundedContextFacades() {
        var projectFacade = mock(ProjectContextFacade.class);
        var environmentalFacade = mock(EnvironmentalMonitoringContextFacade.class);
        var incidentFacade = mock(IncidentContextFacade.class);
        var provider = new IntegratedComplianceDataProvider(projectFacade, environmentalFacade, incidentFacade);

        when(projectFacade.findProjectName(10L)).thenReturn("North Road");
        when(projectFacade.findMonitoringPointIds(10L)).thenReturn(List.of(2L));
        when(environmentalFacade.findMeasurementIdsByMonitoringPointIds(List.of(2L))).thenReturn(List.of(20L));
        when(environmentalFacade.countCompliantMeasurements(List.of(2L))).thenReturn(8);
        when(environmentalFacade.countNonCompliantMeasurements(List.of(2L))).thenReturn(2);
        when(incidentFacade.countOpenIncidents(List.of(20L))).thenReturn(3);
        when(incidentFacade.countCriticalIncidents(List.of(20L))).thenReturn(1);
        when(incidentFacade.averageResolutionHours(List.of(20L))).thenReturn(null);

        assertEquals("North Road", provider.findProjectName(10L));
        assertEquals("Sin información", provider.findContractor(10L));
        assertEquals(8, provider.countCompliantMeasurements(10L));
        assertEquals(2, provider.countNonCompliantMeasurements(10L));
        assertEquals(3, provider.countOpenIncidents(10L));
        assertEquals(1, provider.countCriticalIncidents(10L));
        assertNull(provider.averageResolutionHours(10L));
    }
}
