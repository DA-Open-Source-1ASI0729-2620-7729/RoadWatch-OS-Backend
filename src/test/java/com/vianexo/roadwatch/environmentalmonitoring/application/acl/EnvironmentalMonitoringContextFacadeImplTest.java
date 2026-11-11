package com.vianexo.roadwatch.environmentalmonitoring.application.acl;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalMeasurement;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.RegisterEnvironmentalMeasurementCommand;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetEnvironmentalMeasurementsByMonitoringPointIdQuery;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.IndicatorType;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.MeasurementStatus;
import com.vianexo.roadwatch.environmentalmonitoring.domain.services.EnvironmentalMeasurementQueryService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EnvironmentalMonitoringContextFacadeImplTest {

    @Test
    void exposesMeasurementCountsForMonitoringPoints() {
        var queryService = mock(EnvironmentalMeasurementQueryService.class);
        var facade = new EnvironmentalMonitoringContextFacadeImpl(queryService);
        var compliant = measurement(20L, MeasurementStatus.COMPLIANT);
        var nonCompliant = measurement(21L, MeasurementStatus.NON_COMPLIANT);
        when(queryService.handle(new GetEnvironmentalMeasurementsByMonitoringPointIdQuery(2L)))
                .thenReturn(List.of(compliant, nonCompliant));

        assertEquals(List.of(20L, 21L), facade.findMeasurementIdsByMonitoringPointIds(List.of(2L)));
        assertEquals(1, facade.countCompliantMeasurements(List.of(2L)));
        assertEquals(1, facade.countNonCompliantMeasurements(List.of(2L)));
    }

    private EnvironmentalMeasurement measurement(Long id, MeasurementStatus status) {
        var measurement = new EnvironmentalMeasurement(new RegisterEnvironmentalMeasurementCommand(
                2L, IndicatorType.NOISE, new BigDecimal("70"), LocalDateTime.now()), status);
        measurement.setId(id);
        return measurement;
    }
}
