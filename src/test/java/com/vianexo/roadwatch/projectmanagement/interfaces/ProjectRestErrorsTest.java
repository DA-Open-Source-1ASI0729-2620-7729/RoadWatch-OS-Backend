package com.vianexo.roadwatch.projectmanagement.interfaces;

import com.vianexo.roadwatch.projectmanagement.application.commandservices.ProjectCommandService;
import com.vianexo.roadwatch.projectmanagement.application.queryservices.ProjectQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ProjectOpenApiTest.TestApplication.class)
class ProjectRestErrorsTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ProjectCommandService commandService;

    @Autowired
    private ProjectQueryService queryService;

    @BeforeEach
    void resetMocks() {
        reset(commandService, queryService);
    }

    @Test
    void missingProjectReturnsNotFound() throws Exception {
        when(queryService.getProjectById(42L))
                .thenThrow(new NoSuchElementException("Project not found: 42"));

        mvc().perform(get("/api/v1/projects/42"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }

    @Test
    void archivedProjectReturnsConflict() throws Exception {
        doThrow(new IllegalStateException("Archived projects cannot be changed"))
                .when(commandService).archiveProject(1L);

        mvc().perform(delete("/api/v1/projects/1"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("INVALID_STATE"));
    }

    @Test
    void invalidCreatePayloadReturnsBadRequest() throws Exception {
        mvc().perform(post("/api/v1/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"location\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    void invalidStatusReturnsBadRequest() throws Exception {
        mvc().perform(get("/api/v1/projects?status=UNKNOWN"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    private MockMvc mvc() {
        return MockMvcBuilders.webAppContextSetup(context).build();
    }
}
