package com.vianexo.roadwatch.projectmanagement.interfaces.acl;

import java.util.List;

public interface ProjectContextFacade {

    String findProjectName(Long projectId);

    List<Long> findMonitoringPointIds(Long projectId);
}
