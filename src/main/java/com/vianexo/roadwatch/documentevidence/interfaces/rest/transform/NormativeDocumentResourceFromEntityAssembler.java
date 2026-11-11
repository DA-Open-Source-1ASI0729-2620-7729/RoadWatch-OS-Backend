package com.vianexo.roadwatch.documentevidence.interfaces.rest.transform;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.NormativeDocument;
import com.vianexo.roadwatch.documentevidence.interfaces.rest.resources.NormativeDocumentResource;

public final class NormativeDocumentResourceFromEntityAssembler {

    private NormativeDocumentResourceFromEntityAssembler() {
    }

    public static NormativeDocumentResource toResourceFrom(NormativeDocument document) {
        return new NormativeDocumentResource(
                document.getId(),
                document.getProjectId(),
                document.getName(),
                document.getStatus(),
                document.getCurrentVersion(),
                document.getCreatedAt().toInstant()
        );
    }
}
