package com.vianexo.roadwatch.incidentmitigation.interfaces.acl;

import java.util.List;

public interface IncidentContextFacade {

    Long createIncidentFromAlert(Long measurementId, String title, String description, String severity);

    boolean existsIncidentById(Long incidentId);

    int countOpenIncidents(List<Long> measurementIds);

    int countCriticalIncidents(List<Long> measurementIds);

    Double averageResolutionHours(List<Long> measurementIds);
}
