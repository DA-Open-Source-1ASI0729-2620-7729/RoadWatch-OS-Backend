package com.vianexo.roadwatch.projectmanagement.interfaces.rest;

import com.vianexo.roadwatch.projectmanagement.application.commandservices.ProjectCommandService;
import com.vianexo.roadwatch.projectmanagement.application.queryservices.ProjectQueryService;
import com.vianexo.roadwatch.projectmanagement.domain.model.valueobjects.ProjectStatus;
import com.vianexo.roadwatch.projectmanagement.interfaces.rest.resources.AssignProjectResponsibleResource;
import com.vianexo.roadwatch.projectmanagement.interfaces.rest.resources.CreateProjectResource;
import com.vianexo.roadwatch.projectmanagement.interfaces.rest.resources.ProjectResource;
import com.vianexo.roadwatch.projectmanagement.interfaces.rest.resources.UpdateProjectResource;
import com.vianexo.roadwatch.projectmanagement.interfaces.rest.transform.ProjectResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@Tag(name = "Project Management", description = "Road project management")
public class ProjectController {

    private final ProjectCommandService commandService;
    private final ProjectQueryService queryService;

    public ProjectController(ProjectCommandService commandService, ProjectQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a project")
    @ApiResponse(responseCode = "201", description = "Project created")
    @ApiResponse(responseCode = "400", description = "Invalid project data")
    public ResponseEntity<ProjectResource> createProject(@Valid @RequestBody CreateProjectResource resource) {
        var project = commandService.createProject(resource.name(), resource.description(), resource.location());
        return ResponseEntity.created(URI.create("/api/v1/projects/" + project.getId()))
                .body(ProjectResourceFromEntityAssembler.toResourceFromEntity(project));
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "Get a project by ID")
    @ApiResponse(responseCode = "200", description = "Project found")
    @ApiResponse(responseCode = "400", description = "Invalid project ID")
    @ApiResponse(responseCode = "404", description = "Project not found")
    public ProjectResource getProjectById(@PathVariable Long projectId) {
        return ProjectResourceFromEntityAssembler.toResourceFromEntity(queryService.getProjectById(projectId));
    }

    @GetMapping
    @Operation(summary = "List projects by status")
    @ApiResponse(responseCode = "200", description = "Projects returned")
    @ApiResponse(responseCode = "400", description = "Invalid project status")
    public List<ProjectResource> getProjectsByStatus(
            @Parameter(in = ParameterIn.QUERY, description = "Project status: ACTIVE or ARCHIVED")
            @RequestParam(defaultValue = "ACTIVE") ProjectStatus status) {
        return queryService.getProjectsByStatus(status).stream()
                .map(ProjectResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }

    @PutMapping("/{projectId}")
    @Operation(summary = "Update a project")
    @ApiResponse(responseCode = "200", description = "Project updated")
    @ApiResponse(responseCode = "400", description = "Invalid project data")
    @ApiResponse(responseCode = "404", description = "Project not found")
    @ApiResponse(responseCode = "409", description = "Project is archived")
    public ProjectResource updateProject(@PathVariable Long projectId,
                                         @Valid @RequestBody UpdateProjectResource resource) {
        return ProjectResourceFromEntityAssembler.toResourceFromEntity(
                commandService.updateProject(projectId, resource.name(), resource.description()));
    }

    @PutMapping("/{projectId}/responsible")
    @Operation(summary = "Assign a project responsible user")
    @ApiResponse(responseCode = "200", description = "Responsible user assigned")
    @ApiResponse(responseCode = "400", description = "Invalid user ID")
    @ApiResponse(responseCode = "404", description = "Project not found")
    @ApiResponse(responseCode = "409", description = "Project is archived")
    public ProjectResource assignResponsible(@PathVariable Long projectId,
                                             @Valid @RequestBody AssignProjectResponsibleResource resource) {
        return ProjectResourceFromEntityAssembler.toResourceFromEntity(
                commandService.assignResponsible(projectId, resource.userId()));
    }

    @DeleteMapping("/{projectId}")
    @Operation(summary = "Archive a project")
    @ApiResponse(responseCode = "204", description = "Project archived")
    @ApiResponse(responseCode = "400", description = "Invalid project ID")
    @ApiResponse(responseCode = "404", description = "Project not found")
    @ApiResponse(responseCode = "409", description = "Project is already archived")
    public ResponseEntity<Void> archiveProject(@PathVariable Long projectId) {
        commandService.archiveProject(projectId);
        return ResponseEntity.noContent().build();
    }
}
