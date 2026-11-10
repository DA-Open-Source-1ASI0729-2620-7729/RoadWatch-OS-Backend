package com.vianexo.roadwatch.incidentmitigation.application.acl;

public interface EvidenceClient {

    boolean hasEvidenceForIncident(Long incidentId);
}
