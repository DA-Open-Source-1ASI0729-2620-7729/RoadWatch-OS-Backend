package com.vianexo.roadwatch.documentevidence.interfaces.rest.controllers;

import com.vianexo.roadwatch.documentevidence.application.services.RequiredDocumentService;
import com.vianexo.roadwatch.documentevidence.interfaces.rest.resources.CreateRequiredDocumentResource;
import com.vianexo.roadwatch.documentevidence.interfaces.rest.resources.RequiredDocumentResource;
import com.vianexo.roadwatch.documentevidence.interfaces.rest.transform.RequiredDocumentResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/required-documents")
@CrossOrigin(origins = "${app.cors.allowed-origins:http://localhost:4200}")
public class RequiredDocumentController {

    private final RequiredDocumentService requiredDocumentService;

    public RequiredDocumentController(RequiredDocumentService requiredDocumentService) {
        this.requiredDocumentService = requiredDocumentService;
    }

    @Operation(summary = "Get all required documents")
    @GetMapping
    public ResponseEntity<List<RequiredDocumentResource>> getRequiredDocuments() {
        var resources = requiredDocumentService.findAll().stream()
                .map(RequiredDocumentResourceFromEntityAssembler::toResourceFrom)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Create required document")
    @PostMapping
    public ResponseEntity<RequiredDocumentResource> createRequiredDocument(
            @Valid @RequestBody CreateRequiredDocumentResource resource) {
        var document = requiredDocumentService.defineRequirement(
                resource.projectId(),
                resource.milestoneName(),
                resource.documentName(),
                resource.mandatory(),
                resource.dueDate());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RequiredDocumentResourceFromEntityAssembler.toResourceFrom(document));
    }
}
