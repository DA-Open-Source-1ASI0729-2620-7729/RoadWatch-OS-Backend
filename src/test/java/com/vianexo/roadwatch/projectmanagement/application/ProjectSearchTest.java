package com.vianexo.roadwatch.projectmanagement.application;

import com.vianexo.roadwatch.projectmanagement.application.queryservices.ProjectQueryService;
import com.vianexo.roadwatch.projectmanagement.domain.model.aggregates.Project;
import com.vianexo.roadwatch.projectmanagement.domain.model.valueobjects.ProjectStatus;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.PageResult;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.ProjectRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectSearchTest {

    @Test
    void searchUsesTrimmedNameStatusAndPage() {
        var repository = mock(ProjectRepository.class);
        var project = new Project(1L, "Coastal road", null, "Lima", ProjectStatus.ACTIVE,
                null, null, null);
        var expected = new PageResult<>(List.of(project), 0, 20, 1, 1);
        when(repository.findByNameContaining(ProjectStatus.ACTIVE, "coastal", 0, 20))
                .thenReturn(expected);

        var result = new ProjectQueryService(repository)
                .searchProjectsByName(" coastal ", ProjectStatus.ACTIVE, 0, 20);

        assertEquals(expected, result);
    }

    @Test
    void searchRejectsBlankNameAndInvalidPage() {
        var service = new ProjectQueryService(mock(ProjectRepository.class));

        assertThrows(IllegalArgumentException.class,
                () -> service.searchProjectsByName(" ", ProjectStatus.ACTIVE, 0, 20));
        assertThrows(IllegalArgumentException.class,
                () -> service.searchProjectsByName("road", ProjectStatus.ACTIVE, 0, 101));
    }
}
