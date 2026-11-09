package com.vianexo.roadwatch.projectmanagement.interfaces;

import com.vianexo.roadwatch.projectmanagement.application.commandservices.MonitoringPointCommandService;
import com.vianexo.roadwatch.projectmanagement.application.queryservices.MonitoringPointQueryService;
import com.vianexo.roadwatch.projectmanagement.domain.model.entities.MonitoringPoint;
import com.vianexo.roadwatch.projectmanagement.domain.repositories.PageResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ProjectOpenApiTest.TestApplication.class)
class MonitoringPointRestTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MonitoringPointCommandService commandService;

    @Autowired
    private MonitoringPointQueryService queryService;

    @BeforeEach
    void resetMocks() {
        reset(commandService, queryService);
    }

    @Test
    void createReturnsResourceAndLocation() throws Exception {
        var point = new MonitoringPoint(7L, 1L, "Air", -12.1, -77.0, null, true);
        when(commandService.createPoint(1L, "Air", -12.1, -77.0, null)).thenReturn(point);

        mvc().perform(post("/api/v1/projects/1/monitoring-points")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Air\",\"latitude\":-12.1,\"longitude\":-77.0}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/projects/1/monitoring-points/7"))
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void listReturnsPageMetadata() throws Exception {
        var point = new MonitoringPoint(7L, 1L, "Air", -12.1, -77.0, null, true);
        when(queryService.getPointsByProjectId(1L, 0, 10))
                .thenReturn(new PageResult<>(List.of(point), 0, 10, 1, 1));

        mvc().perform(get("/api/v1/projects/1/monitoring-points?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(7))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void missingCoordinatesReturnBadRequest() throws Exception {
        mvc().perform(post("/api/v1/projects/1/monitoring-points")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Air\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    void missingPointReturnsNotFound() throws Exception {
        when(queryService.getPointById(1L, 99L))
                .thenThrow(new NoSuchElementException("Monitoring point not found: 99"));

        mvc().perform(get("/api/v1/projects/1/monitoring-points/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }

    @Test
    void inactivePointReturnsConflict() throws Exception {
        doThrow(new IllegalStateException("Inactive monitoring points cannot be changed"))
                .when(commandService).deactivatePoint(1L, 7L);

        mvc().perform(delete("/api/v1/projects/1/monitoring-points/7"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("INVALID_STATE"));
    }

    private MockMvc mvc() {
        return MockMvcBuilders.webAppContextSetup(context).build();
    }
}
