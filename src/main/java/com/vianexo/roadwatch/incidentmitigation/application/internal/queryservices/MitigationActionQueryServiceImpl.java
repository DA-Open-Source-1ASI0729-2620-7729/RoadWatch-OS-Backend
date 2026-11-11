package com.vianexo.roadwatch.incidentmitigation.application.internal.queryservices;

import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.MitigationAction;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetMitigationActionsByIncidentIdQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.EnvironmentalIncidentRepository;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.MitigationActionRepository;
import com.vianexo.roadwatch.incidentmitigation.domain.services.MitigationActionQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class MitigationActionQueryServiceImpl implements MitigationActionQueryService {

    private final MitigationActionRepository mitigationActionRepository;
    private final EnvironmentalIncidentRepository incidentRepository;

    public MitigationActionQueryServiceImpl(MitigationActionRepository mitigationActionRepository,
                                            EnvironmentalIncidentRepository incidentRepository) {
        this.mitigationActionRepository = mitigationActionRepository;
        this.incidentRepository = incidentRepository;
    }

    @Override
    public List<MitigationAction> handle(GetMitigationActionsByIncidentIdQuery query) {
        if (!incidentRepository.existsById(query.incidentId())) {
            throw new NoSuchElementException("Incident not found: " + query.incidentId());
        }
        return mitigationActionRepository.findByIncidentId(query.incidentId());
    }
}
