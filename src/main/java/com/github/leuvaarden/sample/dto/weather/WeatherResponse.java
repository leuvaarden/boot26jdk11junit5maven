package com.github.leuvaarden.sample.dto.weather;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WeatherResponse {
    @NotNull
    @ApiModelProperty(value = "Temperature in Celsius", example = "19 °C")
    private String temperature;
    @NotNull
    @ApiModelProperty(value = "Wind speed in km/h", example = "15 km/h")
    private String wind;
    @NotNull
    @ApiModelProperty(value = "Description", example = "Light rain shower")
    private String description;
}
