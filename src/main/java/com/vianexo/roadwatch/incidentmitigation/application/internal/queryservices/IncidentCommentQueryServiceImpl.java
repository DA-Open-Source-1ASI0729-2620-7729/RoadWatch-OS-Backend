package com.vianexo.roadwatch.incidentmitigation.application.internal.queryservices;

import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentComment;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetIncidentCommentsByIncidentIdQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.EnvironmentalIncidentRepository;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.IncidentCommentRepository;
import com.vianexo.roadwatch.incidentmitigation.domain.services.IncidentCommentQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class IncidentCommentQueryServiceImpl implements IncidentCommentQueryService {

    private final IncidentCommentRepository commentRepository;
    private final EnvironmentalIncidentRepository incidentRepository;

    public IncidentCommentQueryServiceImpl(IncidentCommentRepository commentRepository,
                                           EnvironmentalIncidentRepository incidentRepository) {
        this.commentRepository = commentRepository;
        this.incidentRepository = incidentRepository;
    }

    @Override
    public List<IncidentComment> handle(GetIncidentCommentsByIncidentIdQuery query) {
        if (!incidentRepository.existsById(query.incidentId())) {
            throw new NoSuchElementException("Incident not found: " + query.incidentId());
        }
        return commentRepository.findByIncidentId(query.incidentId());
    }
}
