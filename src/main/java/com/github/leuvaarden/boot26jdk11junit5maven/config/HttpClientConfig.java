package com.github.leuvaarden.boot26jdk11junit5maven.config;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.CachingHttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

@Configuration
@EnableCaching
public class HttpClientConfig {

    @Value("${http.timeout.connect:10S}")
    private Duration timeoutConnect;
    @Value("${http.timeout.read:10S}")
    private Duration timeoutRead;
    @Value("${http.pool.size:200}")
    private int poolSize;
    @Value("${http.count.retry:0}")
    private int countRetry;
    @Value("${http.proxy.uri:#{null}}")
    private URI proxyUri;

    @Bean
    public ClientHttpRequestFactory httpComponentsRequestFactory(HttpClient httpClient) {
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Bean
    public HttpClient httpClient(SSLContext sslContext, @Autowired(required = false) HostnameVerifier hostnameVerifier) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Math.toIntExact(timeoutConnect.toMillis()))
                .setConnectionRequestTimeout(Math.toIntExact(timeoutConnect.toMillis()))
                .build();
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(Math.toIntExact(timeoutRead.toMillis()))
                .build();
        return CachingHttpClientBuilder.create()
                .setCacheConfig(CacheConfig.DEFAULT)
                .useSystemProperties()
                .setRetryHandler(countRetry == 0 ? null : new StandardHttpRequestRetryHandler(countRetry, true))
                .setDefaultRequestConfig(requestConfig)
                .setDefaultSocketConfig(socketConfig)
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(hostnameVerifier)
                .setMaxConnTotal(poolSize)
                .setMaxConnPerRoute(poolSize)
                .setProxy(proxyUri == null ? null : new HttpHost(proxyUri.getHost(), getProxyUriPort(), proxyUri.getScheme()))
                .disableCookieManagement()
                .build();
    }

    private int getProxyUriPort() {
        if (proxyUri == null) {
            return -1;
        } else if (proxyUri.getPort() > 0) {
            return proxyUri.getPort();
        } else {
            try {
                return proxyUri.toURL().getDefaultPort();
            } catch (MalformedURLException var2) {
                return -1;
            }
        }
    }
}
