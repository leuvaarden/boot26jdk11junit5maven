package com.github.leuvaarden.sample;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

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
    void testWeatherSuccess() throws Exception {
        mockMvc.perform(get("/example/weather")
                .param("city", "Moscow"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void testWeatherError() throws Exception {
        mockMvc.perform(get("/example/weather")
                .param("city", "Invalid"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").isNotEmpty());
    }
}
