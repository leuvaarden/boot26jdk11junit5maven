package com.github.leuvaarden.boot26jdk11junit5maven.controller;

import com.github.leuvaarden.boot26jdk11junit5maven.dao.ExampleEntity;
import com.github.leuvaarden.boot26jdk11junit5maven.dto.common.Response;
import com.github.leuvaarden.boot26jdk11junit5maven.dto.weather.WeatherResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

@Validated
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public interface ExampleServiceMeta {

    @GetMapping("/weather")
    @Operation(summary = "Returns current weather")
    Response<WeatherResponse> getWeather(
            @NotNull @RequestParam @Parameter(description = "City name", example = "Moscow") String city
    );

    @GetMapping("/credentials")
    @Operation(summary = "Returns decrypted token credentials")
    Response<Object> getCredentials();

    @PostMapping("/entity")
    @Operation(summary = "Creates entity and returns it")
    Response<ExampleEntity> createEntity(
            @NotNull @RequestParam @Parameter(description = "Value for entity", example = "value") String value
    );

    @GetMapping("/entity/{id}")
    @Operation(summary = "Returns entity")
    Response<ExampleEntity> getEntity(
            @NotNull @PathVariable @Parameter(description = "Entity identifier", example = "1") long id
    );
}
