package com.vianexo.roadwatch.projectmanagement.interfaces;

import com.vianexo.roadwatch.projectmanagement.application.commandservices.MonitoringPointCommandService;
import com.vianexo.roadwatch.projectmanagement.application.commandservices.ProjectCommandService;
import com.vianexo.roadwatch.projectmanagement.application.queryservices.MonitoringPointQueryService;
import com.vianexo.roadwatch.projectmanagement.application.queryservices.ProjectQueryService;
import com.vianexo.roadwatch.projectmanagement.interfaces.rest.MonitoringPointController;
import com.vianexo.roadwatch.projectmanagement.interfaces.rest.ProjectController;
import com.vianexo.roadwatch.shared.infrastructure.documentation.openapi.configuration.OpenApiConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.data.jpa.autoconfigure.DataJpaRepositoriesAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ProjectOpenApiTest.TestApplication.class)
class ProjectOpenApiTest {

    @Autowired
    private WebApplicationContext context;

    @Test
    void projectAndMonitoringPointEndpointsAppearInOpenApi() throws Exception {
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(context).build();
        String specification = mvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertTrue(specification.contains("/api/v1/projects"));
        assertTrue(specification.contains("/api/v1/projects/{projectId}"));
        assertTrue(specification.contains("/api/v1/projects/{projectId}/monitoring-points"));
        assertTrue(specification.contains("/api/v1/projects/{projectId}/monitoring-points/{pointId}"));
        var uiResponse = mvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse();
        assertTrue(uiResponse.getRedirectedUrl().contains("swagger-ui"));
        mvc.perform(get(uiResponse.getRedirectedUrl()))
                .andExpect(status().isOk());
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration(exclude = {
            DataSourceAutoConfiguration.class,
            HibernateJpaAutoConfiguration.class,
            DataJpaRepositoriesAutoConfiguration.class
    })
    @Import({ProjectController.class, MonitoringPointController.class, OpenApiConfiguration.class})
    static class TestApplication {

        @Bean
        ProjectCommandService projectCommandService() {
            return mock(ProjectCommandService.class);
        }

        @Bean
        ProjectQueryService projectQueryService() {
            return mock(ProjectQueryService.class);
        }

        @Bean
        MonitoringPointCommandService monitoringPointCommandService() {
            return mock(MonitoringPointCommandService.class);
        }

        @Bean
        MonitoringPointQueryService monitoringPointQueryService() {
            return mock(MonitoringPointQueryService.class);
        }
    }
}
