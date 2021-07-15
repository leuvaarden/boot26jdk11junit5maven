package com.github.leuvaarden.sample;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
        mockMvc.perform(get("/weather")
                .param("city", "Moscow")
                .header(HttpHeaders.ACCEPT_ENCODING, "gzip")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void testWeatherError() throws Exception {
        mockMvc.perform(get("/weather")
                .param("city", "Invalid")
                .header(HttpHeaders.ACCEPT_ENCODING, "gzip")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").isNotEmpty());
    }

    @Test
    void testCurrencySuccess() throws Exception {
        mockMvc.perform(get("/currency")
                .param("date", "2021-07-14")
                .param("currency", "eur")
                .header(HttpHeaders.ACCEPT_ENCODING, "gzip")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void testCurrencyError() throws Exception {
        mockMvc.perform(get("/currency")
                .param("date", "2010-07-14")
                .param("currency", "eur")
                .header(HttpHeaders.ACCEPT_ENCODING, "gzip")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").isNotEmpty());
    }
}
