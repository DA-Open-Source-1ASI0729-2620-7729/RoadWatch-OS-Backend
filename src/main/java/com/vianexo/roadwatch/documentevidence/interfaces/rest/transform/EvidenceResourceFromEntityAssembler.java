package com.vianexo.roadwatch.documentevidence.interfaces.rest.transform;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.Evidence;
import com.vianexo.roadwatch.documentevidence.interfaces.rest.resources.EvidenceResource;

public final class EvidenceResourceFromEntityAssembler {

    private static final String FILE_URL_PREFIX = "/api/v1/evidences/";

    private EvidenceResourceFromEntityAssembler() {

    }

    public static EvidenceResource toResourceFrom(Evidence evidence) {
        return new EvidenceResource(
                evidence.getId(),
                evidence.getIncidentId(),
                evidence.getMeasurementId(),
                evidence.getFileName(),
                FILE_URL_PREFIX + evidence.getId() + "/file",
                evidence.getFileType(),
                evidence.getUploadedByUserId(),
                evidence.getCreatedAt().toInstant()
        );
    }


}
