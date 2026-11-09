package com.vianexo.roadwatch.documentevidence.interfaces.rest.transform;

import com.vianexo.roadwatch.documentevidence.domain.model.aggregates.RequiredDocument;
import com.vianexo.roadwatch.documentevidence.interfaces.rest.resources.RequiredDocumentResource;

public final class RequiredDocumentResourceFromEntityAssembler {

    private RequiredDocumentResourceFromEntityAssembler() {}

    public static RequiredDocumentResource toResourceFrom(RequiredDocument document){
        return new RequiredDocumentResource(
                document.getId(),
                document.getProjectId(),
                document.getMilestoneName(),
                document.getDocumentName(),
                document.isMandatory(),
                document.getDueDate()
        );
    }
}
