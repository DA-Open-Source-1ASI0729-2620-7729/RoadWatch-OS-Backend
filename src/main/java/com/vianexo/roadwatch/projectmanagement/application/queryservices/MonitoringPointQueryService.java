package com.vianexo.roadwatch.projectmanagement.application.queryservices;

import com.vianexo.roadwatch.projectmanagement.domain.model.entities.MonitoringPoint;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.MonitoringPointRepository;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.ProjectRepository;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class MonitoringPointQueryService {

    private final MonitoringPointRepository pointRepository;
    private final ProjectRepository projectRepository;

    public MonitoringPointQueryService(MonitoringPointRepository pointRepository,
                                       ProjectRepository projectRepository) {
        this.pointRepository = pointRepository;
        this.projectRepository = projectRepository;
    }

    public MonitoringPoint getPointById(Long projectId, Long pointId) {
        requireProject(projectId);
        validateId(pointId, "Monitoring point id");
        return pointRepository.findById(pointId)
                .filter(point -> point.getProjectId().equals(projectId))
                .orElseThrow(() -> new NoSuchElementException("Monitoring point not found: " + pointId));
    }

    public List<MonitoringPoint> getPointsByProjectId(Long projectId) {
        requireProject(projectId);
        return pointRepository.findByProjectId(projectId);
    }

    public PageResult<MonitoringPoint> getPointsByProjectId(Long projectId, int page, int size) {
        requireProject(projectId);
        validatePage(page, size);
        return pointRepository.findByProjectId(projectId, page, size);
    }

    private void requireProject(Long projectId) {
        validateId(projectId, "Project id");
        if (projectRepository.findById(projectId).isEmpty()) {
            throw new NoSuchElementException("Project not found: " + projectId);
        }
    }

    private static void validateId(Long id, String field) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(field + " must be positive");
        }
    }

    private static void validatePage(int page, int size) {
        if (page < 0 || size < 1 || size > 100) {
            throw new IllegalArgumentException("Page must be nonnegative and size must be between 1 and 100");
        }
    }
}
