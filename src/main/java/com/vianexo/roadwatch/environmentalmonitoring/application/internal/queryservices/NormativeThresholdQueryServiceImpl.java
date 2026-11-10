package com.vianexo.roadwatch.environmentalmonitoring.application.internal.queryservices;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.NormativeThreshold;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.queries.GetNormativeThresholdsByProjectIdQuery;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.ProjectId;
import com.vianexo.roadwatch.environmentalmonitoring.domain.services.NormativeThresholdQueryService;
import com.vianexo.roadwatch.environmentalmonitoring.domain.repositories.NormativeThresholdRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NormativeThresholdQueryServiceImpl implements NormativeThresholdQueryService {

    private final NormativeThresholdRepository thresholdRepository;

    public NormativeThresholdQueryServiceImpl(NormativeThresholdRepository thresholdRepository) {
        this.thresholdRepository = thresholdRepository;
    }

    @Override
    public List<NormativeThreshold> handle(GetNormativeThresholdsByProjectIdQuery query) {
        return thresholdRepository.findAllByProjectIdAndActiveTrue(new ProjectId(query.projectId()));
    }
}