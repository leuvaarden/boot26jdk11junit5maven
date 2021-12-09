package com.github.leuvaarden.boot26jdk11junit5maven;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = {
        "http.trust.all=true",
        "http.verify.cn=false"})
class TrustAllTest {

    @Resource
    private RestTemplate restTemplate;

    @ParameterizedTest
    @ValueSource(strings = {"https://self-signed.badssl.com/", "https://expired.badssl.com/", "https://wrong.host.badssl.com/"})
    void testTrustAll(String url) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }
}
