package com.github.leuvaarden.boot26jdk11junit5maven.config.http;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.Duration;

@Value
@Component
@ConfigurationProperties(prefix = "http")
public class HttpProperties {
    Duration timeoutConnect = Duration.ofSeconds(10);
    Duration timeoutRead = Duration.ofSeconds(10);
    int maxConnTotal = 200;
    int maxConnRoute = 200;
    URI proxyUri = null;
    int retryCount = 0;
    Duration retryInterval = Duration.ofSeconds(10);
}
