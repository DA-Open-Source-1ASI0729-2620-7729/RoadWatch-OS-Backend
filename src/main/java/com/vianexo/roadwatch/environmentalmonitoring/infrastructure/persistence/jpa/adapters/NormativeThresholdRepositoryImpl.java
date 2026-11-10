package com.vianexo.roadwatch.environmentalmonitoring.infrastructure.persistence.jpa.adapters;

import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.NormativeThreshold;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.IndicatorType;
import com.vianexo.roadwatch.environmentalmonitoring.domain.repositories.NormativeThresholdRepository;
import com.vianexo.roadwatch.environmentalmonitoring.infrastructure.persistence.jpa.repositories.NormativeThresholdJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class NormativeThresholdRepositoryImpl implements NormativeThresholdRepository {

    private final NormativeThresholdJpaRepository jpaRepository;

    public NormativeThresholdRepositoryImpl(NormativeThresholdJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public NormativeThreshold save(NormativeThreshold threshold) {
        return jpaRepository.save(threshold);
    }

    @Override
    public List<NormativeThreshold> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<NormativeThreshold> findByIndicatorType(IndicatorType indicatorType) {
        return jpaRepository.findByIndicatorType(indicatorType);
    }
}
