package com.vianexo.roadwatch.projectmanagement.application.commandservices;

import com.vianexo.roadwatch.projectmanagement.domain.model.entities.MonitoringPoint;
import com.vianexo.roadwatch.projectmanagement.domain.model.valueobjects.ProjectStatus;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.MonitoringPointRepository;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class MonitoringPointCommandService {

    private final MonitoringPointRepository pointRepository;
    private final ProjectRepository projectRepository;

    public MonitoringPointCommandService(MonitoringPointRepository pointRepository,
                                         ProjectRepository projectRepository) {
        this.pointRepository = pointRepository;
        this.projectRepository = projectRepository;
    }

    public MonitoringPoint createPoint(Long projectId, String name, double latitude, double longitude,
                                       Long normativeThresholdId) {
        requireActiveProject(projectId);
        return pointRepository.save(new MonitoringPoint(projectId, name, latitude, longitude,
                normativeThresholdId));
    }

    public MonitoringPoint updatePoint(Long projectId, Long pointId, String name, double latitude,
                                       double longitude, Long normativeThresholdId) {
        requireActiveProject(projectId);
        MonitoringPoint point = findPoint(projectId, pointId);
        point.rename(name);
        point.updateLocation(latitude, longitude);
        point.updateThreshold(normativeThresholdId);
        return pointRepository.save(point);
    }

    public void deactivatePoint(Long projectId, Long pointId) {
        requireActiveProject(projectId);
        MonitoringPoint point = findPoint(projectId, pointId);
        point.deactivate();
        pointRepository.save(point);
    }

    private void requireActiveProject(Long projectId) {
        validateId(projectId, "Project id");
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("Project not found: " + projectId));
        if (project.getStatus() != ProjectStatus.ACTIVE) {
            throw new IllegalStateException("Archived projects cannot be changed");
        }
    }

    private MonitoringPoint findPoint(Long projectId, Long pointId) {
        validateId(pointId, "Monitoring point id");
        return pointRepository.findById(pointId)
                .filter(point -> point.getProjectId().equals(projectId))
                .orElseThrow(() -> new NoSuchElementException("Monitoring point not found: " + pointId));
    }

    private static void validateId(Long id, String field) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(field + " must be positive");
        }
    }
}
