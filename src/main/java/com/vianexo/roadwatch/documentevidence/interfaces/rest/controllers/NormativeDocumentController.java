package com.vianexo.roadwatch.documentevidence.interfaces.rest.controllers;

import com.vianexo.roadwatch.documentevidence.application.services.NormativeDocumentService;
import com.vianexo.roadwatch.documentevidence.interfaces.rest.resources.CreateNormativeDocumentResource;
import com.vianexo.roadwatch.documentevidence.interfaces.rest.resources.NormativeDocumentResource;
import com.vianexo.roadwatch.documentevidence.interfaces.rest.transform.NormativeDocumentResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/normative-documents")
@CrossOrigin(origins = "${app.cors.allowed-origins:http://localhost:4200}")
public class NormativeDocumentController {

    private final NormativeDocumentService documentService;

    public NormativeDocumentController(NormativeDocumentService documentService) {
        this.documentService = documentService;
    }

    @Operation(summary = "Get all normative documents")
    @GetMapping
    public ResponseEntity<List<NormativeDocumentResource>> getDocuments() {
        var resources = documentService.findAll().stream()
                .map(NormativeDocumentResourceFromEntityAssembler::toResourceFrom)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Get normative document by identifier")
    @GetMapping("/{documentId}")
    public ResponseEntity<NormativeDocumentResource> getDocumentById(@PathVariable Long documentId) {
        return documentService.findById(documentId)
                .map(NormativeDocumentResourceFromEntityAssembler::toResourceFrom)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create normative document")
    @PostMapping
    public ResponseEntity<NormativeDocumentResource> createDocument(
            @Valid @RequestBody CreateNormativeDocumentResource resource) {
        var document = documentService.create(resource.projectId(), resource.name());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(NormativeDocumentResourceFromEntityAssembler.toResourceFrom(document));
    }
}