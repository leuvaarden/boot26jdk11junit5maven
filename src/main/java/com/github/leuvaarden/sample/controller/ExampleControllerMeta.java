package com.github.leuvaarden.sample.controller;

import com.github.leuvaarden.sample.dao.ExampleEntity;
import com.github.leuvaarden.sample.dto.common.Response;
import com.github.leuvaarden.sample.dto.currency.Currency;
import com.github.leuvaarden.sample.dto.weather.WeatherResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.rossillo.spring.web.mvc.CacheControl;
import net.rossillo.spring.web.mvc.CachePolicy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RequestMapping(value = "/", produces = APPLICATION_JSON_VALUE)
public interface ExampleControllerMeta {
    @GetMapping("/weather")
    @CacheControl(maxAge = 60 * 10, policy = CachePolicy.PUBLIC)
    @ApiOperation("Returns current weather")
    Response<WeatherResponse> getWeather(@NotNull @RequestParam @ApiParam(value = "City name", example = "Moscow") String city);

    @GetMapping("/currency")
    @ApiOperation("Returns currency rates")
    Response<Map<Currency, Double>> getRates(
            @ApiParam(value = "Desired date", defaultValue = "latest", example = "2021-07-30") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @ApiParam(value = "Target alphabetic currency code", example = "RUB") @RequestParam @NotNull Currency currency);

    @GetMapping("/credentials")
    @PreAuthorize("isAuthenticated()")
    @ApiOperation("Returns decrypted token credentials")
    Response<Object> getCredentials(@NotNull Authentication authentication);

    @PostMapping("/entity")
    @ApiOperation("Creates entity and returns it")
    Response<ExampleEntity> createEntity(@ApiParam(value = "Value for entity", example = "value") @NotNull @RequestParam String value);

    @GetMapping("/entity/{id}")
    @ApiOperation("Returns entity")
    Response<ExampleEntity> getEntity(@ApiParam(value = "Entity identifier", example = "1") @PathVariable long id);
}
