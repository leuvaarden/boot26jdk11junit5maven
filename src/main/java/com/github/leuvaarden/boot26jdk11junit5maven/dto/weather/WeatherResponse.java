package com.github.leuvaarden.boot26jdk11junit5maven.dto.weather;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WeatherResponse {
    @NotNull
    @Schema(description = "Temperature in Celsius", example = "19 °C")
    private String temperature;
    @NotNull
    @Schema(description = "Wind speed in km/h", example = "15 km/h")
    private String wind;
    @NotNull
    @Schema(description = "Description", example = "Light rain shower")
    private String description;
}
