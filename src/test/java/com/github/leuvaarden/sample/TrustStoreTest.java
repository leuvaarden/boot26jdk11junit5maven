package com.github.leuvaarden.sample;

import org.junit.jupiter.api.Test;
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
        "http.truststore.resource=classpath:truststore.p12",
        "http.truststore.type=PKCS12",
        "http.truststore.password=password",
        "http.verify.cn=false"})
public class TrustStoreTest {

    @Resource
    private RestTemplate restTemplate;

    @Test
    void testSelfSigned() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://self-signed.badssl.com/", String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testExpired() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://expired.badssl.com/", String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testWrongHost() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://wrong.host.badssl.com/", String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }
}
