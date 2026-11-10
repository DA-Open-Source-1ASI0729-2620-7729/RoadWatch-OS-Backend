package com.vianexo.roadwatch.incidentmitigation.interfaces.acl;

public interface IncidentContextFacade {

    Long createIncidentFromAlert(Long measurementId, String title, String description, String severity);

    boolean existsIncidentById(Long incidentId);
}
