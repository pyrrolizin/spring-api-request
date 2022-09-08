package com.github.pyrrolizin.springapirequest.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.pyrrolizin.springapirequest.model.Weather;
import com.github.pyrrolizin.springapirequest.service.WeatherApiCaller;

@RestController
public class WeatherController {

    private final WeatherApiCaller weatherApiCallerService;

    private final Logger LOG = LoggerFactory.getLogger(WeatherApiCaller.class);

    @Autowired
    public WeatherController(WeatherApiCaller weatherApiCallerService) {
        this.weatherApiCallerService = weatherApiCallerService;
    }

    @GetMapping("/test")
    public Weather test_weather() {
        LOG.info("GET /test");
        final Weather weather = new Weather("test", 23.3f);
        return weather;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/weather")
    public Weather weather() throws JsonMappingException, JsonProcessingException {
        LOG.info("GET /weather");
        final Weather weather = weatherApiCallerService.getWeather("Stuttgart");
        return weather;
    }
}
