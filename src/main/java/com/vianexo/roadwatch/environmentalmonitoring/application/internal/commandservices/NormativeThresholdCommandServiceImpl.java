package com.vianexo.roadwatch.environmentalmonitoring.application.internal.commandservices;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.NormativeThreshold;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.commands.UpsertNormativeThresholdCommand;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.ProjectId;
import com.vianexo.roadwatch.environmentalmonitoring.domain.services.NormativeThresholdCommandService;
import com.vianexo.roadwatch.environmentalmonitoring.domain.repositories.NormativeThresholdRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NormativeThresholdCommandServiceImpl implements NormativeThresholdCommandService {

    private final NormativeThresholdRepository thresholdRepository;

    public NormativeThresholdCommandServiceImpl(NormativeThresholdRepository thresholdRepository) {
        this.thresholdRepository = thresholdRepository;
    }

    @Override
    @Transactional
    public NormativeThreshold handle(UpsertNormativeThresholdCommand command) {
        var existingThreshold = thresholdRepository.findByProjectIdAndIndicatorTypeAndActiveTrue(
                new ProjectId(command.projectId()),
                command.indicatorType());

        NormativeThreshold threshold;
        if (existingThreshold.isPresent()) {
            threshold = existingThreshold.get();
            threshold.updateLimits(command);
        } else {
            threshold = new NormativeThreshold(command);
        }

        return thresholdRepository.save(threshold);
    }
}
