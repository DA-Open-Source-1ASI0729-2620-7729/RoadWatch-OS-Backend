package com.vianexo.roadwatch.documentevidence.interfaces.rest.controllers;

import com.vianexo.roadwatch.documentevidence.application.services.FileStorageService;
import com.vianexo.roadwatch.documentevidence.application.services.NormativeDocumentService;
import com.vianexo.roadwatch.documentevidence.interfaces.rest.resources.CreateNormativeDocumentResource;
import com.vianexo.roadwatch.documentevidence.interfaces.rest.resources.NormativeDocumentResource;
import com.vianexo.roadwatch.documentevidence.interfaces.rest.transform.NormativeDocumentResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/normative-documents")
@CrossOrigin(origins = "${app.cors.allowed-origins:http://localhost:4200}")
public class NormativeDocumentController {

    private final NormativeDocumentService documentService;
    private final FileStorageService fileStorageService;

    public NormativeDocumentController(NormativeDocumentService documentService,
                                       FileStorageService fileStorageService) {
        this.documentService = documentService;
        this.fileStorageService = fileStorageService;
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

    @Operation(summary = "Upload the file of a normative document")
    @PostMapping(value = "/{documentId}/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NormativeDocumentResource> uploadDocumentFile(
            @PathVariable Long documentId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("uploadedByUserId") Long uploadedByUserId) {
        if (documentService.findById(documentId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var fileName = Objects.requireNonNullElse(file.getOriginalFilename(), "file");
        var storedFileName = fileStorageService.store(file);
        return documentService.registerUpload(documentId, fileName, storedFileName, uploadedByUserId)
                .map(NormativeDocumentResourceFromEntityAssembler::toResourceFrom)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Download the current file of a normative document")
    @GetMapping("/{documentId}/file")
    public ResponseEntity<Resource> downloadDocumentFile(@PathVariable Long documentId) {
        var version = documentService.findCurrentVersion(documentId);
        if (version.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var file = fileStorageService.load(version.get().getStoredFileName());
        if (file.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var contentDisposition = ContentDisposition.attachment()
                .filename(version.get().getFileName(), StandardCharsets.UTF_8)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file.get());
    }
}