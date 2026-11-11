package com.vianexo.roadwatch.incidentmitigation.application.internal.queryservices;

import com.vianexo.roadwatch.incidentmitigation.domain.model.aggregates.EnvironmentalIncident;
import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentStatusHistory;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetAllEnvironmentalIncidentsQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetCriticalIncidentsQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetEnvironmentalIncidentByIdQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetIncidentStatusHistoryQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentSeverity;
import com.vianexo.roadwatch.incidentmitigation.domain.model.valueobjects.IncidentStatus;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.EnvironmentalIncidentRepository;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.IncidentStatusHistoryRepository;
import com.vianexo.roadwatch.incidentmitigation.domain.services.EnvironmentalIncidentQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EnvironmentalIncidentQueryServiceImpl implements EnvironmentalIncidentQueryService {

    private final EnvironmentalIncidentRepository incidentRepository;
    private final IncidentStatusHistoryRepository statusHistoryRepository;

    public EnvironmentalIncidentQueryServiceImpl(EnvironmentalIncidentRepository incidentRepository,
                                                 IncidentStatusHistoryRepository statusHistoryRepository) {
        this.incidentRepository = incidentRepository;
        this.statusHistoryRepository = statusHistoryRepository;
    }

    @Override
    public Optional<EnvironmentalIncident> handle(GetEnvironmentalIncidentByIdQuery query) {
        return incidentRepository.findById(query.incidentId());
    }

    @Override
    public List<EnvironmentalIncident> handle(GetAllEnvironmentalIncidentsQuery query) {
        if (query.status() != null && query.severity() != null) {
            return incidentRepository.findByStatusAndSeverity(query.status(), query.severity());
        }
        if (query.status() != null) {
            return incidentRepository.findByStatus(query.status());
        }
        if (query.severity() != null) {
            return incidentRepository.findBySeverity(query.severity());
        }
        return incidentRepository.findAll();
    }

    @Override
    public List<EnvironmentalIncident> handle(GetCriticalIncidentsQuery query) {
        return incidentRepository.findBySeverityAndStatusNot(IncidentSeverity.CRITICAL, IncidentStatus.CLOSED);
    }

    @Override
    public List<IncidentStatusHistory> handle(GetIncidentStatusHistoryQuery query) {
        if (!incidentRepository.existsById(query.incidentId())) {
            throw new NoSuchElementException("Incident not found: " + query.incidentId());
        }
        return statusHistoryRepository.findByIncidentId(query.incidentId());
    }
}
