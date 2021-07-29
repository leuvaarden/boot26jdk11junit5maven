package com.github.leuvaarden.sample.controller;

import com.github.leuvaarden.sample.dao.ExampleEntity;
import com.github.leuvaarden.sample.dto.Currency;
import com.github.leuvaarden.sample.dto.Response;
import com.github.leuvaarden.sample.dto.WeatherResponse;
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
    Response<WeatherResponse> getWeather(@NotNull @RequestParam String city);

    @GetMapping("/currency")
    Response<Map<Currency, Double>> getRates(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @NotNull Currency currency);

    @GetMapping("/credentials")
    @PreAuthorize("isAuthenticated()")
    Response<Object> getCredentials(@NotNull Authentication authentication);

    @PostMapping("/entity")
    Response<ExampleEntity> createEntity(@NotNull @RequestParam String value);

    @GetMapping("/entity/{id}")
    Response<ExampleEntity> getEntity(@PathVariable long id);
}
