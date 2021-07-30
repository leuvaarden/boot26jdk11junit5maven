package com.github.leuvaarden.sample.controller;

import com.github.leuvaarden.sample.dao.ExampleEntity;
import com.github.leuvaarden.sample.dao.ExampleEntityRepository;
import com.github.leuvaarden.sample.dto.ErrorHolder;
import com.github.leuvaarden.sample.dto.ErrorResponse;
import com.github.leuvaarden.sample.dto.Response;
import com.github.leuvaarden.sample.dto.SuccessResponse;
import com.github.leuvaarden.sample.dto.currency.Currency;
import com.github.leuvaarden.sample.dto.currency.CurrencyResponse;
import com.github.leuvaarden.sample.dto.weather.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

@RestController
public class ExampleController implements ExampleControllerMeta {
    private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);

    private static final String WEATHER_URL = "https://goweather.herokuapp.com/weather/{city}";
    private static final String CURRENCY_URL = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/{date}/currencies/{currencyOne}.min.json";

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private ExampleEntityRepository exampleEntityRepository;

    @Override
    public Response<WeatherResponse> getWeather(@NotNull String city) {
        logger.info("Received city: [{}]", city);
        try {
            WeatherResponse weatherResponse = restTemplate.getForObject(WEATHER_URL, WeatherResponse.class, city);
            logger.info("Returning data: [{}]", weatherResponse);
            return new SuccessResponse<>(weatherResponse);
        } catch (HttpClientErrorException e) {
            ErrorHolder errorHolder = new ErrorHolder(String.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString(), "Bad request");
            logger.info("Returning error: [{}]", errorHolder);
            return new ErrorResponse<>(errorHolder);
        } catch (HttpServerErrorException | UnknownHttpStatusCodeException e) {
            logger.error("Caught", e);
            ErrorHolder errorHolder = new ErrorHolder(String.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString(), "Internal server error");
            logger.info("Returning error: [{}]", errorHolder);
            return new ErrorResponse<>(errorHolder);
        }
    }

    @Override
    public Response<Map<Currency, Double>> getRates(LocalDate date, @NotNull Currency currency) {
        logger.info("Received date: [{}], currency: [{}]", date, currency);
        try {
            CurrencyResponse currencyResponse = restTemplate.getForObject(CURRENCY_URL, CurrencyResponse.class, date == null ? "latest" : date.toString(), currency);
            logger.info("Returning data: [{}]", currencyResponse);
            return new SuccessResponse<>(currencyResponse.getPayload());
        } catch (HttpClientErrorException e) {
            ErrorHolder errorHolder = new ErrorHolder(String.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString(), "Bad request");
            logger.info("Returning error: [{}]", errorHolder);
            return new ErrorResponse<>(errorHolder);
        } catch (HttpServerErrorException | UnknownHttpStatusCodeException e) {
            logger.error("Caught", e);
            ErrorHolder errorHolder = new ErrorHolder(String.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString(), "Internal server error");
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
        ExampleEntity exampleEntity = new ExampleEntity();
        exampleEntity.setValue(value);
        return new SuccessResponse<>(exampleEntityRepository.save(exampleEntity));
    }

    @Override
    public Response<ExampleEntity> getEntity(long id) {
        return exampleEntityRepository.findById(id)
                .map(SuccessResponse::new)
                .map(response -> (Response<ExampleEntity>) response)
                .orElse(new ErrorResponse<>(new ErrorHolder("404", "Not found", "Not found")));
    }
}
