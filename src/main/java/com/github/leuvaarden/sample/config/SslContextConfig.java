package com.github.leuvaarden.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;

@Configuration
public class SslContextConfig {

    @Value("${server.ssl.key-store}")
    private Resource keyStore;
    @Value("${server.ssl.key-store-password}")
    private String keyStorePassword;
    @Value("${server.ssl.key-store-type}")
    private String keyStoreType;

    @Bean
    public KeyManager[] keyManagers() throws GeneralSecurityException, IOException {
        KeyStore keystore = KeyStore.getInstance(this.keyStoreType);
        keystore.load(this.keyStore.getInputStream(), this.keyStorePassword.toCharArray());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keystore, this.keyStorePassword.toCharArray());
        return keyManagerFactory.getKeyManagers();
    }

    @Bean
    public TrustManager[] trustManagers(@Autowired(required = false) KeyStore trustStore) throws GeneralSecurityException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);
        return trustManagerFactory.getTrustManagers();
    }

    @Bean
    public SSLContext sslContext(KeyManager[] keyManagers, TrustManager[] trustManagers) throws GeneralSecurityException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagers, trustManagers, new SecureRandom());
        return sslContext;
    }
}
