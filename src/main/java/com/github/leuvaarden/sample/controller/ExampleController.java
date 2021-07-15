package com.github.leuvaarden.sample.controller;

import com.github.leuvaarden.sample.dto.ErrorResponse;
import com.github.leuvaarden.sample.dto.Response;
import com.github.leuvaarden.sample.dto.SuccessResponse;
import com.github.leuvaarden.sample.dto.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

@RestController
public class ExampleController implements ExampleControllerMeta {
    private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);

    private static final String WEATHER_URL = "https://goweather.herokuapp.com/weather/";

    @Resource
    private RestTemplate restTemplate;

    @Override
    public Response<WeatherResponse> getWeather(@NotNull String city) {
        logger.info("Received city: [{}]", city);
        try {
            ResponseEntity<WeatherResponse> responseEntity = restTemplate.getForEntity(WEATHER_URL + city, WeatherResponse.class);
            logger.info("Received responseEntity: [{}]", responseEntity);
            logger.info("Returning data: [{}]", responseEntity.getBody());
            return new SuccessResponse<>(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            ErrorResponse.ErrorHolder errorHolder = new ErrorResponse.ErrorHolder(String.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString(), "Bad request");
            logger.info("Returning error: [{}]", errorHolder);
            return new ErrorResponse<>(errorHolder);
        } catch (HttpServerErrorException | UnknownHttpStatusCodeException e) {
            logger.error("Received", e);
            ErrorResponse.ErrorHolder errorHolder = new ErrorResponse.ErrorHolder(String.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString(), "Internal server error");
            logger.info("Returning error: [{}]", errorHolder);
            return new ErrorResponse<>(errorHolder);
        }
    }
}
