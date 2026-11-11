package com.vianexo.roadwatch.environmentalmonitoring.application;

import com.vianexo.roadwatch.environmentalmonitoring.application.internal.commandservices.EnvironmentalMeasurementCommandServiceImpl;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalMeasurement;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.NormativeThreshold;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.RegisterEnvironmentalMeasurementCommand;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.UpsertNormativeThresholdCommand;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.IndicatorType;
import com.vianexo.roadwatch.environmentalmonitoring.domain.repositories.EnvironmentalAlertRepository;
import com.vianexo.roadwatch.environmentalmonitoring.domain.repositories.EnvironmentalMeasurementRepository;
import com.vianexo.roadwatch.environmentalmonitoring.domain.repositories.NormativeThresholdRepository;
import com.vianexo.roadwatch.incidentmitigation.application.events.EnvironmentalAlertGeneratedEvent;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EnvironmentalMeasurementIncidentIntegrationTest {

    @Test
    void publishesAnIncidentEventForANonCompliantMeasurement() {
        var measurementRepository = mock(EnvironmentalMeasurementRepository.class);
        var thresholdRepository = mock(NormativeThresholdRepository.class);
        var alertRepository = mock(EnvironmentalAlertRepository.class);
        var eventPublisher = mock(ApplicationEventPublisher.class);
        var threshold = new NormativeThreshold(new UpsertNormativeThresholdCommand(
                IndicatorType.NOISE, "dB", new BigDecimal("70"), new BigDecimal("85")));
        var service = new EnvironmentalMeasurementCommandServiceImpl(
                measurementRepository, thresholdRepository, alertRepository, eventPublisher);

        when(thresholdRepository.findByIndicatorType(IndicatorType.NOISE)).thenReturn(Optional.of(threshold));
        when(measurementRepository.save(any(EnvironmentalMeasurement.class))).thenAnswer(invocation -> {
            var measurement = invocation.getArgument(0, EnvironmentalMeasurement.class);
            measurement.setId(10L);
            return measurement;
        });

        service.handle(new RegisterEnvironmentalMeasurementCommand(
                4L, IndicatorType.NOISE, new BigDecimal("90"), LocalDateTime.now()));

        var eventCaptor = ArgumentCaptor.forClass(EnvironmentalAlertGeneratedEvent.class);
        verify(eventPublisher).publishEvent(eventCaptor.capture());
        var event = eventCaptor.getValue();
        assertEquals(10L, event.measurementId());
        assertEquals("Environmental threshold exceeded", event.title());
        assertEquals("Environmental threshold exceeded", event.description());
        assertEquals("HIGH", event.severity());
    }
}
