package com.github.leuvaarden.boot26jdk11junit5maven.controller;

import com.github.leuvaarden.boot26jdk11junit5maven.dao.ExampleEntity;
import com.github.leuvaarden.boot26jdk11junit5maven.dto.common.ErrorHolder;
import com.github.leuvaarden.boot26jdk11junit5maven.dto.common.ErrorResponse;
import com.github.leuvaarden.boot26jdk11junit5maven.dto.common.Response;
import com.github.leuvaarden.boot26jdk11junit5maven.dto.common.SuccessResponse;
import com.github.leuvaarden.boot26jdk11junit5maven.dto.weather.WeatherResponse;
import com.github.leuvaarden.boot26jdk11junit5maven.service.ExampleEntityService;
import com.github.leuvaarden.boot26jdk11junit5maven.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
public class ExampleController implements ExampleControllerMeta {

    @Resource
    private WeatherService weatherService;
    @Resource
    private ExampleEntityService exampleEntityService;

    @Override
    public Response<WeatherResponse> getWeather(@NotNull String city) {
        logger.info("Received city: [{}]", city);
        try {
            WeatherResponse weatherResponse = weatherService.get(city);
            logger.info("Returning data: [{}]", weatherResponse);
            return new SuccessResponse<>(weatherResponse);
        } catch (HttpClientErrorException e) {
            ErrorHolder errorHolder = new ErrorHolder(e.getRawStatusCode(), e.getResponseBodyAsString(), "Bad request");
            logger.info("Returning error: [{}]", errorHolder);
            return new ErrorResponse<>(errorHolder);
        } catch (HttpServerErrorException | UnknownHttpStatusCodeException e) {
            logger.error("Caught", e);
            ErrorHolder errorHolder = new ErrorHolder(e.getRawStatusCode(), e.getResponseBodyAsString(), "Internal server error");
            logger.info("Returning error: [{}]", errorHolder);
            return new ErrorResponse<>(errorHolder);
        }
    }

    @Override
    public Response<Object> getCredentials(@NotNull Authentication authentication) {
        return new SuccessResponse<>(authentication.getCredentials());
    }

    @Override
    public Response<ExampleEntity> createEntity(String value) {
        return new SuccessResponse<>(exampleEntityService.create(value));
    }

    @Override
    public Response<ExampleEntity> getEntity(long id) {
        return exampleEntityService.get(id)
                .map(SuccessResponse::new)
                .map(response -> (Response<ExampleEntity>) response)
                .orElse(new ErrorResponse<>(new ErrorHolder(404, "Not found", "Not found")));
    }
}
