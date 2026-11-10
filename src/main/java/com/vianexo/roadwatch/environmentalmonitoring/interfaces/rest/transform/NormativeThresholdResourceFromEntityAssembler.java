package com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.transform;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.NormativeThreshold;
import com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.resources.NormativeThresholdResource;

public class NormativeThresholdResourceFromEntityAssembler {
    public static NormativeThresholdResource toResourceFromEntity(NormativeThreshold entity) {
        return new NormativeThresholdResource(
                entity.getId(),
                entity.getProjectId().projectId(),
                entity.getIndicatorType(),
                entity.getUnit(),
                entity.getWarningLimit(),
                entity.getMaximumLimit(),
                entity.getNormativeReference(),
                entity.isActive()
        );
    }
}
