package com.vianexo.roadwatch.incidentmitigation.domain.repositories;

import com.vianexo.roadwatch.incidentmitigation.domain.model.entities.IncidentStatusHistory;

import java.util.List;

public interface IncidentStatusHistoryRepository {

    IncidentStatusHistory save(IncidentStatusHistory history);

    List<IncidentStatusHistory> findByIncidentId(Long incidentId);
}
