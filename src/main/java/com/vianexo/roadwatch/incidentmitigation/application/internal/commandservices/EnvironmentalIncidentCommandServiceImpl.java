package com.vianexo.roadwatch.incidentmitigation.application.internal.commandservices;

import com.vianexo.roadwatch.incidentmitigation.application.acl.EvidenceClient;
import com.vianexo.roadwatch.incidentmitigation.domain.model.aggregates.EnvironmentalIncident;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.ChangeIncidentSeverityCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.ChangeIncidentStatusCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.CreateEnvironmentalIncidentCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.MarkOverdueIncidentsCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentStatusHistory;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentStatus;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.EnvironmentalIncidentRepository;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.IncidentStatusHistoryRepository;
import com.vianexo.roadwatch.incidentmitigation.domain.services.EnvironmentalIncidentCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class EnvironmentalIncidentCommandServiceImpl implements EnvironmentalIncidentCommandService {

    private final EnvironmentalIncidentRepository incidentRepository;
    private final IncidentStatusHistoryRepository statusHistoryRepository;
    private final Optional<EvidenceClient> evidenceClient;

    @Autowired
    public EnvironmentalIncidentCommandServiceImpl(EnvironmentalIncidentRepository incidentRepository,
                                                   IncidentStatusHistoryRepository statusHistoryRepository,
                                                   Optional<EvidenceClient> evidenceClient) {
        this.incidentRepository = incidentRepository;
        this.statusHistoryRepository = statusHistoryRepository;
        this.evidenceClient = evidenceClient;
    }

    public EnvironmentalIncidentCommandServiceImpl(EnvironmentalIncidentRepository incidentRepository,
                                                   IncidentStatusHistoryRepository statusHistoryRepository) {
        this(incidentRepository, statusHistoryRepository, Optional.empty());
    }

    @Override
    public EnvironmentalIncident handle(CreateEnvironmentalIncidentCommand command) {
        var incident = new EnvironmentalIncident(command.measurementId(), command.title(), command.description(),
                command.severity());
        return incidentRepository.save(incident);
    }

    @Override
    public EnvironmentalIncident handle(ChangeIncidentStatusCommand command) {
        var incident = findIncident(command.incidentId());
        if (command.status() == IncidentStatus.CLOSED) {
            requireEvidence(incident.getId());
        }
        var previousStatus = incident.getStatus();
        incident.changeStatus(command.status());
        var saved = incidentRepository.save(incident);
        statusHistoryRepository.save(new IncidentStatusHistory(saved.getId(), previousStatus, saved.getStatus(),
                command.changedByUserId()));
        return saved;
    }

    @Override
    public EnvironmentalIncident handle(ChangeIncidentSeverityCommand command) {
        var incident = findIncident(command.incidentId());
        incident.changeSeverity(command.severity());
        return incidentRepository.save(incident);
    }

    @Override
    public List<EnvironmentalIncident> handle(MarkOverdueIncidentsCommand command) {
        var cutoff = LocalDateTime.now().minusDays(command.maxOpenDays());
        return incidentRepository.findByStatusInAndCreatedAtBefore(
                        List.of(IncidentStatus.OPEN, IncidentStatus.IN_PROGRESS), cutoff).stream()
                .map(incident -> {
                    var previousStatus = incident.getStatus();
                    incident.markOverdue();
                    var saved = incidentRepository.save(incident);
                    statusHistoryRepository.save(new IncidentStatusHistory(saved.getId(), previousStatus,
                            saved.getStatus(), command.changedByUserId()));
                    return saved;
                })
                .toList();
    }

    private EnvironmentalIncident findIncident(Long incidentId) {
        return incidentRepository.findById(incidentId)
                .orElseThrow(() -> new NoSuchElementException("Incident not found: " + incidentId));
    }

    private void requireEvidence(Long incidentId) {
        var client = evidenceClient.orElseThrow(() -> new IllegalStateException(
                "Evidence client is not available"));
        if (!client.hasEvidenceForIncident(incidentId)) {
            throw new IllegalStateException("Evidence is required before closing an incident");
        }
    }
}
