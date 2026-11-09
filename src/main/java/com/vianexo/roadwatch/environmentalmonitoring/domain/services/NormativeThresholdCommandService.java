package com.vianexo.roadwatch.environmentalmonitoring.domain.services;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.NormativeThreshold;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.UpsertNormativeThresholdCommand;

import java.util.Optional;

public interface NormativeThresholdCommandService {
    Optional<NormativeThreshold> handle(UpsertNormativeThresholdCommand command);
}