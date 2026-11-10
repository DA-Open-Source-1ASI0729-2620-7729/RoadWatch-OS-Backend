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
    public EnvironmentalMeasurement handle(RegisterEnvironmentalMeasurementCommand command) {
        var threshold = thresholdRepository.findByProjectIdAndIndicatorTypeAndActiveTrue(
                new ProjectId(command.projectId()),
                command.indicatorType());
        BigDecimal appliedLimit = BigDecimal.ZERO;
        MeasurementStatus status = MeasurementStatus.COMPLIANT;

        if (threshold.isPresent()) {
            appliedLimit = threshold.get().getMaximumLimit();
            status = threshold.get().evaluate(command.measuredValue());
        }

        var measurement = new EnvironmentalMeasurement(command, appliedLimit, status);
        var savedMeasurement = measurementRepository.save(measurement);

        if (status == MeasurementStatus.WARNING || status == MeasurementStatus.NON_COMPLIANT) {
            AlertSeverity severity;

            if (status == MeasurementStatus.NON_COMPLIANT) {
                severity = AlertSeverity.CRITICAL;
            } else {
                severity = AlertSeverity.HIGH;
            }

            var alert = new EnvironmentalAlert(
                    command.projectId(),
                    command.monitoringPointId(),
                    savedMeasurement.getId(),
                    command.indicatorType(),
                    command.measuredValue(),
                    appliedLimit,
                    severity,
                    "Environmental threshold exceeded");
            alertRepository.save(alert);
        }

        return savedMeasurement;
    }
}
