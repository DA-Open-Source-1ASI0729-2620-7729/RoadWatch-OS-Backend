package com.vianexo.roadwatch.incidentmitigation.infrastructure.acl;

import com.vianexo.roadwatch.documentevidence.interfaces.acl.DocumentEvidenceContextFacade;
import com.vianexo.roadwatch.incidentmitigation.application.acl.EvidenceClient;
import org.springframework.stereotype.Component;

@Component
public class DocumentEvidenceClientAdapter implements EvidenceClient {

    private final DocumentEvidenceContextFacade documentEvidenceContextFacade;

    public DocumentEvidenceClientAdapter(DocumentEvidenceContextFacade documentEvidenceContextFacade) {
        this.documentEvidenceContextFacade = documentEvidenceContextFacade;
    }

    @Override
    public boolean hasEvidenceForIncident(Long incidentId) {
        return documentEvidenceContextFacade.hasEvidenceForIncident(incidentId);
    }
}
