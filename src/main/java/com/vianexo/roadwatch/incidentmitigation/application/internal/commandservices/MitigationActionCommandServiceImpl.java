package com.vianexo.roadwatch.incidentmitigation.application.internal.commandservices;

import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.CompleteMitigationActionCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.RegisterMitigationActionCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.MitigationAction;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.EnvironmentalIncidentRepository;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.MitigationActionRepository;
import com.vianexo.roadwatch.incidentmitigation.domain.services.MitigationActionCommandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class MitigationActionCommandServiceImpl implements MitigationActionCommandService {

    private final MitigationActionRepository mitigationActionRepository;
    private final EnvironmentalIncidentRepository incidentRepository;

    public MitigationActionCommandServiceImpl(MitigationActionRepository mitigationActionRepository,
                                              EnvironmentalIncidentRepository incidentRepository) {
        this.mitigationActionRepository = mitigationActionRepository;
        this.incidentRepository = incidentRepository;
    }

    @Override
    public MitigationAction handle(RegisterMitigationActionCommand command) {
        requireOpenIncident(command.incidentId());
        return mitigationActionRepository.save(new MitigationAction(command.incidentId(), command.description(),
                command.responsibleUserId()));
    }

    @Override
    public MitigationAction handle(CompleteMitigationActionCommand command) {
        requireOpenIncident(command.incidentId());
        var action = mitigationActionRepository.findById(command.mitigationActionId())
                .filter(found -> found.getIncidentId().equals(command.incidentId()))
                .orElseThrow(() -> new NoSuchElementException(
                        "Mitigation action not found: " + command.mitigationActionId()));
        action.complete();
        return mitigationActionRepository.save(action);
    }

    private void requireOpenIncident(Long incidentId) {
        incidentRepository.findById(incidentId)
                .orElseThrow(() -> new NoSuchElementException("Incident not found: " + incidentId))
                .requireNotClosed();
    }
}
