package com.github.leuvaarden.boot26jdk11junit5maven.service;

import com.github.leuvaarden.boot26jdk11junit5maven.dto.weather.WeatherResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Resource;

@Component
public class WeatherService {
    private static final String WEATHER_URL = "https://goweather.herokuapp.com/weather/{city}";

    @Resource
    private WebClient weatherWebClient;

    public WeatherResponse get(@NotNull String city) {
        return weatherWebClient.method(HttpMethod.GET)
                .uri(WEATHER_URL, city)
                .retrieve()
                .bodyToMono(WeatherResponse.class)
                .block();
    }
}
