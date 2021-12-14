package com.github.leuvaarden.boot26jdk11junit5maven.config.http;

import org.apache.hc.client5.http.HttpRequestRetryStrategy;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.client5.http.nio.AsyncClientConnectionManager;
import org.apache.hc.client5.http.ssl.DefaultClientTlsStrategy;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.nio.ssl.TlsStrategy;
import org.apache.hc.core5.util.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.HttpComponentsClientHttpConnector;

import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Profile("apache-client")
@Configuration
public class ApacheConnectorSupplier implements Supplier<ClientHttpConnector> {

    @Resource
    private HttpProperties httpProperties;
    @Resource
    private SSLContext sslContext;
    @Autowired(required = false)
    private HostnameVerifier hostnameVerifier;

    @Override
    public ClientHttpConnector get() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(httpProperties.getTimeoutConnect().toMillis(), TimeUnit.MILLISECONDS)
                .setResponseTimeout(httpProperties.getTimeoutRead().toMillis(), TimeUnit.MILLISECONDS)
                .build();
        TlsStrategy tlsStrategy = new DefaultClientTlsStrategy(sslContext, hostnameVerifier);
        AsyncClientConnectionManager asyncClientConnectionManager = PoolingAsyncClientConnectionManagerBuilder.create()
                .useSystemProperties()
                .setMaxConnPerRoute(httpProperties.getMaxConnRoute())
                .setMaxConnTotal(httpProperties.getMaxConnTotal())
                .setTlsStrategy(tlsStrategy)
                .build();
        HttpRequestRetryStrategy httpRequestRetryStrategy = new DefaultHttpRequestRetryStrategy(httpProperties.getRetryCount(), TimeValue.of(httpProperties.getRetryInterval().toMillis(), TimeUnit.MILLISECONDS));
        HttpHost httpHost = getProxy(httpProperties.getProxyUri());
        CloseableHttpAsyncClient closeableHttpAsyncClient = HttpAsyncClients.custom()
                .useSystemProperties()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(asyncClientConnectionManager)
                .setRetryStrategy(httpRequestRetryStrategy)
                .setProxy(httpHost)
                .build();
        return new HttpComponentsClientHttpConnector(closeableHttpAsyncClient);
    }

    private HttpHost getProxy(URI proxyUri) {
        if (proxyUri == null) {
            return null;
        }
        String scheme = proxyUri.getScheme();
        String hostname = proxyUri.getHost();
        int port = resolvePort(proxyUri);
        return new HttpHost(scheme, hostname, port);
    }

    private int resolvePort(URI proxyUri) {
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
