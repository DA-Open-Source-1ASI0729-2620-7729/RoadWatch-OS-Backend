package com.vianexo.roadwatch.documentevidence.application.services;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.Evidence;
import com.vianexo.roadwatch.documentevidence.domain.repositories.EvidenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvidenceService {

    private final EvidenceRepository evidenceRepository;

    public EvidenceService(EvidenceRepository evidenceRepository) {
        this.evidenceRepository = evidenceRepository;
    }

    public List<Evidence> findAll() {
        return evidenceRepository.findAll();
    }

    public Optional<Evidence> findById(Long id) {
        return evidenceRepository.findById(id);
    }

    public Evidence create(Long incidentId, Long measurementId, String fileName, String fileType,
                           String storedFileName, Long uploadedByUserId) {
        return evidenceRepository.save(new Evidence(
                incidentId, measurementId, fileName, fileType, storedFileName, uploadedByUserId));
    }
}