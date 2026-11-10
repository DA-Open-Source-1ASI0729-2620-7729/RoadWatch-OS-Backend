package com.vianexo.roadwatch.environmentalmonitoring.domain.repositories;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalAlert;

import java.util.List;
import java.util.Optional;

public interface EnvironmentalAlertRepository {
    EnvironmentalAlert save(EnvironmentalAlert alert);
    Optional<EnvironmentalAlert> findById(Long id);
    List<EnvironmentalAlert> findAll();
    List<EnvironmentalAlert> findTop10ByOrderByCreatedAtDesc();
}
