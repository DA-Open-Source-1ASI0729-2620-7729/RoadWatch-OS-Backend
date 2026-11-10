package com.vianexo.roadwatch.incidentmitigation.interfaces.rest;

import com.vianexo.roadwatch.incidentmitigation.domain.model.commands.AddIncidentCommentCommand;
import com.vianexo.roadwatch.incidentmitigation.domain.model.queries.GetIncidentCommentsByIncidentIdQuery;
import com.vianexo.roadwatch.incidentmitigation.domain.services.IncidentCommentCommandService;
import com.vianexo.roadwatch.incidentmitigation.domain.services.IncidentCommentQueryService;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources.AddIncidentCommentResource;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.resources.IncidentCommentResource;
import com.vianexo.roadwatch.incidentmitigation.interfaces.rest.transform.IncidentCommentResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/incidents/{incidentId}/comments")
@Tag(name = "Incident & Mitigation", description = "Environmental incident management")
public class IncidentCommentsController {

    private final IncidentCommentCommandService commandService;
    private final IncidentCommentQueryService queryService;

    public IncidentCommentsController(IncidentCommentCommandService commandService,
                                      IncidentCommentQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Add a comment to an incident")
    @ApiResponse(responseCode = "201", description = "Comment added")
    @ApiResponse(responseCode = "400", description = "Invalid comment data")
    @ApiResponse(responseCode = "404", description = "Incident not found")
    @ApiResponse(responseCode = "409", description = "Incident is closed")
    public ResponseEntity<IncidentCommentResource> addComment(@PathVariable Long incidentId,
                                                              @Valid @RequestBody AddIncidentCommentResource resource) {
        var comment = commandService.handle(new AddIncidentCommentCommand(incidentId, resource.authorUserId(),
                resource.content()));
        return ResponseEntity.created(URI.create("/api/v1/incidents/%d/comments".formatted(incidentId)))
                .body(IncidentCommentResourceFromEntityAssembler.toResourceFromEntity(comment));
    }

    @GetMapping
    @Operation(summary = "List the comments of an incident")
    @ApiResponse(responseCode = "200", description = "Comments returned")
    @ApiResponse(responseCode = "404", description = "Incident not found")
    public List<IncidentCommentResource> getComments(@PathVariable Long incidentId) {
        return queryService.handle(new GetIncidentCommentsByIncidentIdQuery(incidentId)).stream()
                .map(IncidentCommentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }
}
