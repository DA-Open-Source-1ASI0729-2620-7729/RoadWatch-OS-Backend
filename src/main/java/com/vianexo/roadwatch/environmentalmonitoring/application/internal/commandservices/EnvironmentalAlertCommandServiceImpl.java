package com.vianexo.roadwatch.environmentalmonitoring.application.internal.commandservices;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalAlert;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.AcknowledgeEnvironmentalAlertCommand;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.ResolveEnvironmentalAlertCommand;
import com.vianexo.roadwatch.environmentalmonitoring.domain.services.EnvironmentalAlertCommandService;
import com.vianexo.roadwatch.environmentalmonitoring.domain.repositories.EnvironmentalAlertRepository;
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
        var alert = alertRepository.findById(command.alertId());

        if (alert.isEmpty()) {
            return Optional.empty();
        }

        alert.get().acknowledge();
        return Optional.of(alertRepository.save(alert.get()));
    }

    @Override
    @Transactional
    public Optional<EnvironmentalAlert> handle(ResolveEnvironmentalAlertCommand command) {
        var alert = alertRepository.findById(command.alertId());

        if (alert.isEmpty()) {
            return Optional.empty();
        }

        alert.get().resolve();
        return Optional.of(alertRepository.save(alert.get()));
    }
}
