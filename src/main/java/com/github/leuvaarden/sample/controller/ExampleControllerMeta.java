package com.github.leuvaarden.sample.controller;

import com.github.leuvaarden.sample.dto.Response;
import com.github.leuvaarden.sample.dto.WeatherResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RequestMapping(value = "/example", produces = APPLICATION_JSON_VALUE)
public interface ExampleControllerMeta {
    @GetMapping("/weather")
    Response<WeatherResponse> getWeather(@NotNull @RequestParam String city);
}
