package com.vianexo.roadwatch.environmentalmonitoring.domain.services;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.NormativeThreshold;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetNormativeThresholdsByProjectIdQuery;

import java.util.List;

public interface NormativeThresholdQueryService {
    List<NormativeThreshold> handle(GetNormativeThresholdsByProjectIdQuery query);
}