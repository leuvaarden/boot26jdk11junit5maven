package com.github.leuvaarden.sample.config;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.SSLContext;
import java.net.MalformedURLException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;

@Configuration
public class HttpConfig {

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
    @Value("${http.verify.hostname:true}")
    private boolean verifyHostname;

    @Bean
    public ClientHttpRequestFactory httpComponentsRequestFactory(HttpClient httpClient) throws NoSuchAlgorithmException {
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Bean
    public HttpClient httpClient(SSLContext sslContext) throws NoSuchAlgorithmException {
        return HttpClientBuilder.create()
                .useSystemProperties()
                .setRetryHandler(countRetry == 0 ? null : new StandardHttpRequestRetryHandler(countRetry, true))
                .setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(Math.toIntExact(timeoutConnect.toMillis())).setConnectionRequestTimeout(Math.toIntExact(timeoutConnect.toMillis())).build())
                .setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(Math.toIntExact(timeoutRead.toMillis())).build())
                .setSSLContext(sslContext != null ? sslContext : SSLContext.getDefault())
                .setSSLHostnameVerifier(verifyHostname ? null : NoopHostnameVerifier.INSTANCE)
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
