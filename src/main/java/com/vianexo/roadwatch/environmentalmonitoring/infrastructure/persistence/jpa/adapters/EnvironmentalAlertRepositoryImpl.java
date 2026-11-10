package com.vianexo.roadwatch.environmentalmonitoring.infrastructure.persistence.jpa.adapters;
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.aggregates.EnvironmentalAlert; 
import com.vianexo.roadwatch.environmentalmonitoring.domain.model.valueobjects.ProjectId;
import com.vianexo.roadwatch.environmentalmonitoring.domain.repositories.EnvironmentalAlertRepository;
import com.vianexo.roadwatch.environmentalmonitoring.infrastructure.persistence.jpa.repositories.EnvironmentalAlertJpaRepository; 
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public class EnvironmentalAlertRepositoryImpl implements EnvironmentalAlertRepository {

    private final EnvironmentalAlertJpaRepository jpaRepository;

    public EnvironmentalAlertRepositoryImpl(EnvironmentalAlertJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public EnvironmentalAlert save(EnvironmentalAlert alert) {
        return jpaRepository.save(alert);
    }

    @Override
    public Optional<EnvironmentalAlert> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<EnvironmentalAlert> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<EnvironmentalAlert> findAllByProjectIdOrderByCreatedAtDesc(ProjectId projectId) {
        return jpaRepository.findAllByProjectIdOrderByCreatedAtDesc(projectId);
    }
}