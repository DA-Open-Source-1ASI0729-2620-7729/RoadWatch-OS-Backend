package com.vianexo.roadwatch.projectmanagement.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.projectmanagement.domain.model.entities.MonitoringPoint;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.MonitoringPointRepository;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.PageResult;
import com.vianexo.roadwatch.projectmanagement.infrastructure.persistence.jpa.entities.MonitoringPointJpaEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class MonitoringPointRepositoryAdapter implements MonitoringPointRepository {

    private final MonitoringPointJpaRepository jpaRepository;
    private final ProjectJpaRepository projectJpaRepository;

    public MonitoringPointRepositoryAdapter(MonitoringPointJpaRepository jpaRepository,
                                            ProjectJpaRepository projectJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.projectJpaRepository = projectJpaRepository;
    }

    @Override
    public MonitoringPoint save(MonitoringPoint point) {
        MonitoringPointJpaEntity entity;
        if (point.getId() == null) {
            entity = new MonitoringPointJpaEntity(projectJpaRepository.getReferenceById(point.getProjectId()),
                    point.getName(),
                    BigDecimal.valueOf(point.getLatitude()), BigDecimal.valueOf(point.getLongitude()),
                    point.getNormativeThresholdId(), point.isActive());
        } else {
            entity = jpaRepository.findById(point.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Monitoring point not found: " + point.getId()));
            if (!entity.getProjectId().equals(point.getProjectId())) {
                throw new IllegalArgumentException("Monitoring point project cannot be changed");
            }
            entity.update(point.getName(), BigDecimal.valueOf(point.getLatitude()),
                    BigDecimal.valueOf(point.getLongitude()), point.getNormativeThresholdId(), point.isActive());
        }
        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<MonitoringPoint> findById(Long id) {
        return jpaRepository.findById(id).map(MonitoringPointRepositoryAdapter::toDomain);
    }

    @Override
    public List<MonitoringPoint> findByProjectId(Long projectId) {
        return jpaRepository.findByProject_Id(projectId).stream()
                .map(MonitoringPointRepositoryAdapter::toDomain)
                .toList();
    }

    @Override
    public PageResult<MonitoringPoint> findByProjectId(Long projectId, int page, int size) {
        var result = jpaRepository.findByProject_Id(projectId, PageRequest.of(page, size, Sort.by("id")));
        return new PageResult<>(result.getContent().stream()
                .map(MonitoringPointRepositoryAdapter::toDomain).toList(),
                page, size, result.getTotalElements(), result.getTotalPages());
    }

    private static MonitoringPoint toDomain(MonitoringPointJpaEntity entity) {
        return new MonitoringPoint(entity.getId(), entity.getProjectId(), entity.getName(),
                entity.getLatitude().doubleValue(), entity.getLongitude().doubleValue(),
                entity.getNormativeThresholdId(), entity.isActive());
    }
}
