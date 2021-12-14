package com.github.leuvaarden.boot26jdk11junit5maven;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AppTest {

    @Resource
    private MockMvc mockMvc;

    @Test
    @Disabled("Flaky")
    void testWeatherSuccess() throws Exception {
        mockMvc.perform(get("/weather")
                        .param("city", "Moscow")
                        .header(HttpHeaders.ACCEPT_ENCODING, "gzip")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    @Disabled("Flaky")
    void testWeatherError() throws Exception {
        mockMvc.perform(get("/weather")
                        .param("city", UUID.randomUUID().toString())
                        .header(HttpHeaders.ACCEPT_ENCODING, "gzip")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").isNotEmpty());
    }

    @Test
    @WithMockUser
    void testCredentialsSuccess() throws Exception {
        mockMvc.perform(get("/credentials"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void testCredentialsError() throws Exception {
        mockMvc.perform(get("/credentials"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").isNotEmpty());
    }
}
