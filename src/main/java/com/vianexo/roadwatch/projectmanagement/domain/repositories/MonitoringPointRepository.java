package com.vianexo.roadwatch.projectmanagement.domain.repositories;

import com.vianexo.roadwatch.projectmanagement.domain.model.entities.MonitoringPoint;

import java.util.List;
import java.util.Optional;

public interface MonitoringPointRepository {

    MonitoringPoint save(MonitoringPoint point);

    Optional<MonitoringPoint> findById(Long id);

    List<MonitoringPoint> findByProjectId(Long projectId);

}
