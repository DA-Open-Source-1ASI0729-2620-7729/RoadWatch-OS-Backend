package com.vianexo.roadwatch.environmentalmonitoring.application.internal.commandservices;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalAlert;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalMeasurement;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.RegisterEnvironmentalMeasurementCommand;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.MeasurementStatus;
import com.vianexo.roadwatch.environmentalmonitoring.domain.services.EnvironmentalMeasurementCommandService;

import com.vianexo.roadwatch.environmentalmonitoring.domain.repositories.EnvironmentalAlertRepository;
import com.vianexo.roadwatch.environmentalmonitoring.domain.repositories.EnvironmentalMeasurementRepository;
import com.vianexo.roadwatch.environmentalmonitoring.domain.repositories.NormativeThresholdRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        var threshold = thresholdRepository.findByIndicatorType(command.indicatorType());
        MeasurementStatus status = MeasurementStatus.COMPLIANT;

        if (threshold.isPresent()) {
            if (threshold.get().isExceeded(command.value())) {
                status = MeasurementStatus.NON_COMPLIANT;
            } else if (threshold.get().isWarning(command.value())) {
                status = MeasurementStatus.WARNING;
            }
        }

        var measurement = new EnvironmentalMeasurement(command, status);
        var savedMeasurement = measurementRepository.save(measurement);

        if (status == MeasurementStatus.WARNING || status == MeasurementStatus.NON_COMPLIANT) {
            var alert = new EnvironmentalAlert(
                    savedMeasurement.getId(),
                    "Environmental threshold exceeded");
            alertRepository.save(alert);
        }

        return savedMeasurement;
    }
}
