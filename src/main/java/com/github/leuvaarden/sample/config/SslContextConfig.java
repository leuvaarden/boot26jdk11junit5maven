package com.github.leuvaarden.sample.config;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

@Configuration
public class SslContextConfig {

    @Bean
    @ConditionalOnProperty(value = "http.verify.cn", havingValue = "false")
    public HostnameVerifier trustAllHostnameVerifier() {
        return NoopHostnameVerifier.INSTANCE;
    }

    @Bean
    @ConditionalOnMissingBean(SSLContext.class)
    @ConditionalOnProperty(value = "http.truststore.resource")
    public SSLContext truststoreSslContext(@Value("${http.truststore.resource}") Resource truststoreResource,
                                           @Value("${http.truststore.password}") String truststorePassword,
                                           @Value("${http.truststore.type}") String truststoreType
    ) throws GeneralSecurityException, IOException {
        KeyStore keyStore = KeyStore.getInstance(truststoreType);
        keyStore.load(truststoreResource.getInputStream(), truststorePassword.toCharArray());
        return SSLContextBuilder.create()
                .loadTrustMaterial(keyStore, null)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(SSLContext.class)
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
}
