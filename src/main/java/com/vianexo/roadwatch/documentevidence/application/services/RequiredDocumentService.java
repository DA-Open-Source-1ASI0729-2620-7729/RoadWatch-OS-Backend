package com.vianexo.roadwatch.documentevidence.application.services;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.RequiredDocument;
import com.vianexo.roadwatch.documentevidence.domain.repositories.RequiredDocumentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RequiredDocumentService {

    private final RequiredDocumentRepository requiredDocumentRepository;

    public RequiredDocumentService(RequiredDocumentRepository requiredDocumentRepository) {
        this.requiredDocumentRepository = requiredDocumentRepository;
    }

    public List<RequiredDocument> findAll() {
        return requiredDocumentRepository.findAll();
    }

    public RequiredDocument create(Long projectId, String milestoneName, String documentName,
                                   boolean mandatory, LocalDate dueDate) {
        return requiredDocumentRepository.save(
                new RequiredDocument(projectId, milestoneName, documentName, mandatory, dueDate));
    }
}
