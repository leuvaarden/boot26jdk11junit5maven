package com.github.leuvaarden.sample.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WeatherResponse {
    @NotNull
    private String temperature;
    @NotNull
    private String wind;
    @NotNull
    private String description;
}
