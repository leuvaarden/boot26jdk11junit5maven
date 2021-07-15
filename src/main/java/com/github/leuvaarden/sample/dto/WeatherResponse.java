package com.github.leuvaarden.sample.dto;

import javax.validation.constraints.NotNull;

public class WeatherResponse {
    @NotNull
    private String temperature;
    @NotNull
    private String wind;
    @NotNull
    private String description;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "temperature='" + temperature + '\'' +
                ", wind='" + wind + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
