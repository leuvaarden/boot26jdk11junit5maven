package com.github.leuvaarden.boot26jdk11junit5maven;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = {
        "http.trust.all=true",
        "http.verify.cn=false"})
class TrustAllTest {

    @Resource
    private WebClient weatherWebClient;

    @ParameterizedTest
    @ValueSource(strings = {"https://self-signed.badssl.com/", "https://expired.badssl.com/", "https://wrong.host.badssl.com/"})
    void testTrustAll(String url) {
        ResponseEntity<String> responseEntity = weatherWebClient.get()
                .uri(url)
                .retrieve()
                .toEntity(String.class)
                .block();
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }
}
