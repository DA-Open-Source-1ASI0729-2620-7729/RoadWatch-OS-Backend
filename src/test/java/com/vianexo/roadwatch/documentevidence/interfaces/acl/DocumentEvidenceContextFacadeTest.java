package com.vianexo.roadwatch.documentevidence.interfaces.acl;

import com.vianexo.roadwatch.documentevidence.application.services.EvidenceService;
import com.vianexo.roadwatch.documentevidence.application.services.NormativeDocumentService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DocumentEvidenceContextFacadeTest {

    private final EvidenceService evidenceService = mock(EvidenceService.class);
    private final DocumentEvidenceContextFacade facade = new DocumentEvidenceContextFacade(
            mock(NormativeDocumentService.class), evidenceService);

    @Test
    void exposesEvidenceForIncident() {
        when(evidenceService.hasEvidenceForIncident(10L)).thenReturn(true);

        assertTrue(facade.hasEvidenceForIncident(10L));
    }
}
