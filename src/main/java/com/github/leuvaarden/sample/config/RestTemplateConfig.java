package com.github.leuvaarden.sample.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.validation.constraints.NotNull;
import java.security.GeneralSecurityException;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Bean
    @ConditionalOnProperty(value = "http.trust.all", havingValue = "true")
    public SSLContext trustAllSslContext() throws GeneralSecurityException {
        return SSLContextBuilder.create()
                .loadTrustMaterial(TrustAllStrategy.INSTANCE)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(SSLContext.class)
    public SSLContext defaultSslContext() throws GeneralSecurityException {
        return SSLContext.getDefault();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory, ObjectMapper objectMapper) {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        HttpMessageConverter<Object> hmc = new MappingJackson2HttpMessageConverter(objectMapper);
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
