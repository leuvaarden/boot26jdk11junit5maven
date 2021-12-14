package com.github.leuvaarden.boot26jdk11junit5maven.config.http;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpProxy;
import org.eclipse.jetty.client.Origin;
import org.eclipse.jetty.client.ProxyConfiguration;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.JettyClientHttpConnector;

import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Profile("jetty-client")
@Configuration
public class JettyConnectorSupplier implements Supplier<ClientHttpConnector> {

    @Resource
    private HttpProperties httpProperties;
    @Resource
    private SSLContext sslContext;
    @Autowired(required = false)
    private HostnameVerifier hostnameVerifier;

    @Override
    public ClientHttpConnector get() {
        SslContextFactory.Client sslContextFactory = new SslContextFactory.Client();
        sslContextFactory.setSslContext(sslContext);
        sslContextFactory.setHostnameVerifier(hostnameVerifier);
        Executor executor = new QueuedThreadPool(httpProperties.getMaxConnTotal());
        HttpClient httpClient = new HttpClient(sslContextFactory);
        httpClient.setExecutor(executor);
        Origin.Address address = getAddress(httpProperties.getProxyUri());
        if (address != null) {
            HttpProxy httpProxy = new HttpProxy(address, sslContextFactory);
            ProxyConfiguration proxyConfiguration = httpClient.getProxyConfiguration();
            proxyConfiguration.getProxies().add(httpProxy);
        }
        httpClient.setConnectTimeout(httpProperties.getTimeoutConnect().toMillis());
        httpClient.setMaxConnectionsPerDestination(httpProperties.getMaxConnRoute());
        return new JettyClientHttpConnector(httpClient);
    }

    private Origin.Address getAddress(URI proxyUri) {
        if (proxyUri == null) {
            return null;
        }
        String hostname = proxyUri.getHost();
        int port = resolvePort(proxyUri);
        return new Origin.Address(hostname, port);
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
