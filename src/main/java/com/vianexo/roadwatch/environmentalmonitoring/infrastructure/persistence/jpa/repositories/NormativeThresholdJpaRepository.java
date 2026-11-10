package com.vianexo.roadwatch.environmentalmonitoring.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.NormativeThreshold;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.IndicatorType;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.ProjectId;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NormativeThresholdJpaRepository extends JpaRepository<NormativeThreshold, Long> {
    List<NormativeThreshold> findAllByProjectIdAndActiveTrue(ProjectId projectId);
    Optional<NormativeThreshold> findByProjectIdAndIndicatorTypeAndActiveTrue(ProjectId projectId, IndicatorType indicatorType);
}