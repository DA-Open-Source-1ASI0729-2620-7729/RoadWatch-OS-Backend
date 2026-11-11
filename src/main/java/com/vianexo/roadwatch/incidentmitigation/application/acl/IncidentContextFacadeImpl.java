package com.vianexo.roadwatch.incidentmitigation.application.acl;

import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.CreateEnvironmentalIncidentCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetAllEnvironmentalIncidentsQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetCriticalIncidentsQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetEnvironmentalIncidentByIdQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetIncidentStatusHistoryQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentStatus;
import com.vianexo.roadwatch.incidentmitigation.domain.services.EnvironmentalIncidentCommandService;
import com.vianexo.roadwatch.incidentmitigation.domain.services.EnvironmentalIncidentQueryService;
import com.vianexo.roadwatch.incidentmitigation.interfaces.acl.IncidentContextFacade;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
@Service
public class IncidentContextFacadeImpl implements IncidentContextFacade {

    private final EnvironmentalIncidentCommandService commandService;
    private final EnvironmentalIncidentQueryService queryService;

    public IncidentContextFacadeImpl(EnvironmentalIncidentCommandService commandService,
                                     EnvironmentalIncidentQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Override
    public Long createIncidentFromAlert(Long measurementId, String title, String description, String severity) {
        var command = new CreateEnvironmentalIncidentCommand(measurementId, title, description,
                IncidentSeverity.valueOf(severity));
        return commandService.handle(command).getId();
    }

    @Override
    public boolean existsIncidentById(Long incidentId) {
        if (incidentId == null || incidentId <= 0) {
            return false;
        }
        return queryService.handle(new GetEnvironmentalIncidentByIdQuery(incidentId)).isPresent();
    }

    @Override
    public int countOpenIncidents(List<Long> measurementIds) {
        return (int) queryService.handle(new GetAllEnvironmentalIncidentsQuery(null, null)).stream()
                .filter(incident -> measurementIds.contains(incident.getMeasurementId()))
                .filter(incident -> incident.getStatus() == IncidentStatus.OPEN
                        || incident.getStatus() == IncidentStatus.IN_PROGRESS
                        || incident.getStatus() == IncidentStatus.OVERDUE)
                .count();
    }

    @Override
    public int countCriticalIncidents(List<Long> measurementIds) {
        return (int) queryService.handle(new GetCriticalIncidentsQuery()).stream()
                .filter(incident -> measurementIds.contains(incident.getMeasurementId()))
                .count();
    }

    @Override
    public Double averageResolutionHours(List<Long> measurementIds) {
        var resolutionHours = queryService.handle(new GetAllEnvironmentalIncidentsQuery(null, null)).stream()
                .filter(incident -> measurementIds.contains(incident.getMeasurementId()))
                .filter(incident -> incident.getStatus() == IncidentStatus.CLOSED)
                .filter(incident -> incident.getCreatedAt() != null)
                .flatMap(incident -> queryService.handle(new GetIncidentStatusHistoryQuery(incident.getId())).stream()
                        .filter(history -> history.getNewStatus() == IncidentStatus.CLOSED)
                        .filter(history -> history.getChangedAt() != null)
                        .map(history -> Duration.between(incident.getCreatedAt(), history.getChangedAt())
                                .toMinutes() / 60.0))
                .toList();

        if (resolutionHours.isEmpty()) {
            return null;
        }
        return resolutionHours.stream().mapToDouble(Double::doubleValue).average().orElseThrow();
    }
}
