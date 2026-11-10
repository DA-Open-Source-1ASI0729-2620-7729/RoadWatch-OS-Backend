package com.vianexo.roadwatch.environmentalmonitoring.application.internal.commandservices;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalAlert;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.AcknowledgeEnvironmentalAlertCommand;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.ResolveEnvironmentalAlertCommand;
import com.vianexo.roadwatch.environmentalmonitoring.domain.services.EnvironmentalAlertCommandService;
import com.vianexo.roadwatch.environmentalmonitoring.infrastructure.persistence.jpa.repositories.EnvironmentalAlertRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EnvironmentalAlertCommandServiceImpl implements EnvironmentalAlertCommandService {

    private final EnvironmentalAlertRepository alertRepository;

    public EnvironmentalAlertCommandServiceImpl(EnvironmentalAlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    @Transactional
    public Optional<EnvironmentalAlert> handle(AcknowledgeEnvironmentalAlertCommand command) {
        return alertRepository.findById(command.alertId()).map(alert -> {
            alert.acknowledge();
            return alertRepository.save(alert);
        });
    }

    @Override
    @Transactional
    public Optional<EnvironmentalAlert> handle(ResolveEnvironmentalAlertCommand command) {
        return alertRepository.findById(command.alertId()).map(alert -> {
            alert.resolve();
            return alertRepository.save(alert);
        });
    }
}