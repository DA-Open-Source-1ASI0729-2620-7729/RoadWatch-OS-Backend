package com.vianexo.roadwatch.environmentalmonitoring.domain.repositories;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.NormativeThreshold;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.IndicatorType;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.ProjectId;

import java.util.List;
import java.util.Optional;

public interface NormativeThresholdRepository {
    NormativeThreshold save(NormativeThreshold threshold);
    List<NormativeThreshold> findAllByProjectIdAndActiveTrue(ProjectId projectId);
    Optional<NormativeThreshold> findByProjectIdAndIndicatorTypeAndActiveTrue(ProjectId projectId, IndicatorType indicatorType);
}