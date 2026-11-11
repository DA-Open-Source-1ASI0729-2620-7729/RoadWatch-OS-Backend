package com.vianexo.roadwatch.incidentmitigation.application.internal.commandservices;

import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.AddIncidentCommentCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentComment;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.EnvironmentalIncidentRepository;
import com.vianexo.roadwatch.incidentmitigation.domain.repositories.IncidentCommentRepository;
import com.vianexo.roadwatch.incidentmitigation.domain.services.IncidentCommentCommandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class IncidentCommentCommandServiceImpl implements IncidentCommentCommandService {

    private final IncidentCommentRepository commentRepository;
    private final EnvironmentalIncidentRepository incidentRepository;

    public IncidentCommentCommandServiceImpl(IncidentCommentRepository commentRepository,
                                             EnvironmentalIncidentRepository incidentRepository) {
        this.commentRepository = commentRepository;
        this.incidentRepository = incidentRepository;
    }

    @Override
    public IncidentComment handle(AddIncidentCommentCommand command) {
        incidentRepository.findById(command.incidentId())
                .orElseThrow(() -> new NoSuchElementException("Incident not found: " + command.incidentId()))
                .requireNotClosed();
        return commentRepository.save(new IncidentComment(command.incidentId(), command.authorUserId(),
                command.content()));
    }
}
