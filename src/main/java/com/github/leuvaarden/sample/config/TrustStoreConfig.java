package com.github.leuvaarden.sample.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

@Configuration
@ConditionalOnProperty("server.ssl.trust-store")
public class TrustStoreConfig {

    @Value("${server.ssl.trust-store}")
    private Resource trustStore;
    @Value("${server.ssl.trust-store-password}")
    private String trustStorePassword;
    @Value("${server.ssl.trust-store-type}")
    private String trustStoreType;

    @Bean
    public KeyStore trustStore() throws GeneralSecurityException, IOException {
        KeyStore truststore = KeyStore.getInstance(this.trustStoreType);
        truststore.load(this.trustStore.getInputStream(), this.trustStorePassword.toCharArray());
        return truststore;
    }
}
