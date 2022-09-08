package com.github.pyrrolizin.springapirequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.pyrrolizin.springapirequest.model.Weather;
import com.github.pyrrolizin.springapirequest.service.WeatherApiCaller;

@SpringBootTest
class SpringApiRequestApplicationTests {

	private final WeatherApiCaller weatherApiCallerService;

	@Autowired
	public SpringApiRequestApplicationTests(WeatherApiCaller weatherApiCallerService) {
		this.weatherApiCallerService = weatherApiCallerService;
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void givenUrl_whenFetched_thenWeatherIsReceived() throws JsonMappingException, JsonProcessingException {

		// Given

		// When
		Weather weather = weatherApiCallerService.getWeather("Stuttgart");

		// Then
		assertNotEquals(weather, null);
	}

}
