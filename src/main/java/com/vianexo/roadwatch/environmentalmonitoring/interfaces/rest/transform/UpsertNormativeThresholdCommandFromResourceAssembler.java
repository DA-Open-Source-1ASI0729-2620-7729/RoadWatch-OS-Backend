package com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.transform;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.UpsertNormativeThresholdCommand;
import com.vianexo.roadwatch.environmentalmonitoring.interfaces.rest.resources.UpsertNormativeThresholdResource;

public class UpsertNormativeThresholdCommandFromResourceAssembler {
    public static UpsertNormativeThresholdCommand toCommandFromResource(UpsertNormativeThresholdResource resource) {
        return new UpsertNormativeThresholdCommand(
                resource.projectId(),
                resource.indicatorType(),
                resource.unit(),
                resource.warningLimit(),
                resource.maximumLimit(),
                resource.normativeReference()
        );
    }
}
