package com.github.leuvaarden.sample.service;

import com.github.leuvaarden.sample.dto.weather.WeatherResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
public class WeatherService {
    private static final String WEATHER_URL = "https://goweather.herokuapp.com/weather/{city}";

    @Resource
    private RestTemplate weatherRestTemplate;

    public WeatherResponse get(@NotNull String city) {
        return weatherRestTemplate.getForObject(WEATHER_URL, WeatherResponse.class, city);
    }
}
