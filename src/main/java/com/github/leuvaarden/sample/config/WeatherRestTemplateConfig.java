package com.github.leuvaarden.sample.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.util.List;

@Configuration
public class WeatherRestTemplateConfig {

    @Bean
    public ObjectMapper weatherObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    @Bean
    public RestTemplate weatherRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory, ObjectMapper weatherObjectMapper) {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        HttpMessageConverter<Object> hmc = new MappingJackson2HttpMessageConverter(weatherObjectMapper);
        restTemplate.getMessageConverters().set(indexOfJacksonConverter(restTemplate.getMessageConverters()), hmc);
        return restTemplate;
    }

    private int indexOfJacksonConverter(@NotNull List<HttpMessageConverter<?>> httpMessageConverters) {
        for (int i = 0; i < httpMessageConverters.size(); i++) {
            if (httpMessageConverters.get(i) instanceof MappingJackson2HttpMessageConverter) {
                return i;
            }
        }
        return -1;
    }
}
