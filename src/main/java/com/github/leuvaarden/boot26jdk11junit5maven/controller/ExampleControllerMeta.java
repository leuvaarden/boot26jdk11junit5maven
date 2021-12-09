package com.github.leuvaarden.boot26jdk11junit5maven.controller;

import com.github.leuvaarden.boot26jdk11junit5maven.dao.ExampleEntity;
import com.github.leuvaarden.boot26jdk11junit5maven.dto.common.Response;
import com.github.leuvaarden.boot26jdk11junit5maven.dto.weather.WeatherResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import net.rossillo.spring.web.mvc.CacheControl;
import net.rossillo.spring.web.mvc.CachePolicy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RequestMapping(value = "/", produces = APPLICATION_JSON_VALUE)
public interface ExampleControllerMeta {

    @GetMapping("/weather")
    @CacheControl(maxAge = 60 * 10, policy = CachePolicy.PUBLIC)
    @Operation(summary = "Returns current weather")
    Response<WeatherResponse> getWeather(@NotNull @RequestParam @Parameter(description = "City name", example = "Moscow") String city);

    @GetMapping("/credentials")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Returns decrypted token credentials")
    Response<Object> getCredentials(@NotNull Authentication authentication);

    @PostMapping("/entity")
    @Operation(summary = "Creates entity and returns it")
    Response<ExampleEntity> createEntity(@Parameter(description = "Value for entity", example = "value") @NotNull @RequestParam String value);

    @GetMapping("/entity/{id}")
    @Operation(summary = "Returns entity")
    Response<ExampleEntity> getEntity(@Parameter(description = "Entity identifier", example = "1") @PathVariable long id);
}
