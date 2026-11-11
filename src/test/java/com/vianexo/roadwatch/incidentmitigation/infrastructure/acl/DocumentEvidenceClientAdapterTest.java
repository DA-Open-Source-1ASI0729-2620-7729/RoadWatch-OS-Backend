package com.vianexo.roadwatch.incidentmitigation.infrastructure.acl;

import com.vianexo.roadwatch.documentevidence.interfaces.acl.DocumentEvidenceContextFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DocumentEvidenceClientAdapterTest {

    @Test
    void delegatesEvidenceValidationToTheDocumentEvidenceFacade() {
        var facade = mock(DocumentEvidenceContextFacade.class);
        var adapter = new DocumentEvidenceClientAdapter(facade);
        when(facade.hasEvidenceForIncident(10L)).thenReturn(true);

        assertTrue(adapter.hasEvidenceForIncident(10L));
        verify(facade).hasEvidenceForIncident(10L);
    }
}
