package com.vianexo.roadwatch.environmentalmonitoring.application.internal.commandservices;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalAlert;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalMeasurement;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.RegisterEnvironmentalMeasurementCommand;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.AlertSeverity;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.MeasurementStatus;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.ProjectId;
import com.vianexo.roadwatch.environmentalmonitoring.domain.services.EnvironmentalMeasurementCommandService;

import com.vianexo.roadwatch.environmentalmonitoring.domain.repositories.EnvironmentalAlertRepository;
import com.vianexo.roadwatch.environmentalmonitoring.domain.repositories.EnvironmentalMeasurementRepository;
import com.vianexo.roadwatch.environmentalmonitoring.domain.repositories.NormativeThresholdRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class EnvironmentalMeasurementCommandServiceImpl implements EnvironmentalMeasurementCommandService {

    private final EnvironmentalMeasurementRepository measurementRepository;
    private final NormativeThresholdRepository thresholdRepository;
    private final EnvironmentalAlertRepository alertRepository;

    public EnvironmentalMeasurementCommandServiceImpl(
            EnvironmentalMeasurementRepository measurementRepository,
            NormativeThresholdRepository thresholdRepository,
            EnvironmentalAlertRepository alertRepository
    ) {
        this.measurementRepository = measurementRepository;
        this.thresholdRepository = thresholdRepository;
        this.alertRepository = alertRepository;
    }

    @Override
    @Transactional
    public Optional<EnvironmentalMeasurement> handle(RegisterEnvironmentalMeasurementCommand command) {
        var thresholdOpt = thresholdRepository.findByProjectIdAndIndicatorTypeAndActiveTrue(
                new ProjectId(command.projectId()),
                command.indicatorType()
        );

        BigDecimal appliedLimit = thresholdOpt
                .map(t -> t.getMaxPermissibleLimit())
                .orElse(BigDecimal.ZERO);

        MeasurementStatus evaluatedStatus = thresholdOpt
                .map(t -> t.evaluate(command.measuredValue()))
                .orElse(MeasurementStatus.COMPLIANT);

        var measurement = new EnvironmentalMeasurement(command, appliedLimit, evaluatedStatus);
        var savedMeasurement = measurementRepository.save(measurement);

        if (evaluatedStatus == MeasurementStatus.WARNING || evaluatedStatus == MeasurementStatus.CRITICAL) {
            AlertSeverity severity = evaluatedStatus == MeasurementStatus.CRITICAL
                    ? AlertSeverity.CRITICAL
                    : AlertSeverity.HIGH;

            String alertMessage = String.format(
                    "Indicator %s recorded %s %s (limit: %s %s)",
                    command.indicatorType().name(),
                    command.measuredValue(),
                    command.unit(),
                    appliedLimit,
                    command.unit()
            );

            var alert = new EnvironmentalAlert(
                    command.projectId(),
                    command.monitoringPointId(),
                    savedMeasurement.getId(),
                    command.indicatorType(),
                    command.measuredValue(),
                    appliedLimit,
                    severity,
                    alertMessage
            );
            alertRepository.save(alert);
        }

        return Optional.of(savedMeasurement);
    }
}