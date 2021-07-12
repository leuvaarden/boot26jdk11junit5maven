package com.github.leuvaarden.testgenericresponse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AppTest {

    @Resource
    private MockMvc mockMvc;

    @Test
    void testExampleGetSuccess() throws Exception {
        mockMvc.perform(get("/example/get")
                .accept(APPLICATION_JSON)
                .param("context", randomUUID().toString())
                .param("success", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andDo(print());
    }

    @Test
    void testExampleGetError() throws Exception {
        mockMvc.perform(get("/example/get")
                .accept(APPLICATION_JSON)
                .param("context", randomUUID().toString())
                .param("success", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andDo(print());
    }
}
