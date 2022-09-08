package com.github.pyrrolizin.springapirequest.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pyrrolizin.springapirequest.model.Weather;

@Service
public class WeatherApiCaller {

    @Value("${config.apikey}")
    private String apikey;

    private final String apiurl = "https://api.openweathermap.org/data/2.5/weather?q={city},de&units=metric&APPID={apikey}";

    @Autowired
    private ObjectMapper objectMapper;

    private final Logger LOG = LoggerFactory.getLogger(WeatherApiCaller.class);

    /*
     * private String getWeatherMock() {
     * final String responseString =
     * "{\"coord\":{\"lon\":9.177,\"lat\":48.7823},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"base\":\"stations\",\"main\":{\"temp\":19.16,\"feels_like\":19.4,\"temp_min\":17.91,\"temp_max\":19.89,\"pressure\":1012,\"humidity\":87},\"visibility\":10000,\"wind\":{\"speed\":1.03,\"deg\":0},\"rain\":{\"1h\":0.21},\"clouds\":{\"all\":92},\"dt\":1662591309,\"sys\":{\"type\":1,\"id\":1274,\"country\":\"DE\",\"sunrise\":1662612591,\"sunset\":1662659557},\"timezone\":7200,\"id\":2825297,\"name\":\"Stuttgart\",\"cod\":200}"
     * ;
     * 
     * return responseString;
     * }
     */

    private String getWeatherApi(String city) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiurl.replace("{city}", city).replace("{apikey}", apikey)))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;

        try {
            response = HttpClient.newHttpClient().send(request,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        final String responseString = response.body();

        return responseString;

    }

    @Cacheable(value = "weatherapi", key = "#city")
    public Weather getWeather(String city) throws JsonMappingException, JsonProcessingException {
        LOG.info("get current weather from external API");

        final String responseString = getWeatherApi(city);
        JsonNode jo = objectMapper.readTree(responseString);

        /*
         * condition: JSON["weather"][0]["description"]
         * temperature: JSON["main"]["temp"]
         */

        final String condition = jo.get("weather").get(0).get("description").asText();
        final float temperature = (float) Math.round(jo.get("main").get("temp").floatValue() * 10.0f) / 10.0f;

        return new Weather(condition, temperature);
    }
}
