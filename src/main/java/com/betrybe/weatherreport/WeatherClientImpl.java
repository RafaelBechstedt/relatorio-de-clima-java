package com.betrybe.weatherreport;

import com.betrybe.weatherreport.interfaces.WeatherClient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Javadoc.
 */
@RestController
public class WeatherClientImpl implements WeatherClient {


  private final RestTemplate restTemplate;
  private final ObjectMapper mapper;

  @Autowired
  public WeatherClientImpl(RestTemplate restTemplate, ObjectMapper mapper) {
    this.restTemplate = restTemplate;
    this.mapper = mapper;
  }


  @Override
  public String getWeather(String city) {
    Geocode geocode = getGeocode(city);
    String currentWeather = getCurrentWeather(geocode);
    return String.format("temperatura atual de %s em %s", currentWeather, city);
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  record Geocode(String latitude, String longitude) {

  }

  private Geocode getGeocode(String city) {
    // Pega o resultado da chamada como um JSON em texto
    String json = restTemplate.getForObject(getGeocodeUrl(city), String.class);

    // Faz a interpretação do JSON em uma árvore
    JsonNode jsonNode = null;
    try {
      jsonNode = mapper.readTree(json);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    // Acessa o primeiro (0) item da lista no atributo "result"
    JsonNode cityNode = jsonNode.at("/results/0");

    // Converte o item para a classe Geocode
    try {
      return mapper.treeToValue(cityNode, Geocode.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  private String getGeocodeUrl(String city) {
    return String.format("https://geocoding-api.open-meteo.com/v1/search?language=pt_BR&name=%s",
        city);
  }

  private String getCurrentWeather(Geocode geocode) {
    WeatherResult weatherResult = restTemplate.getForObject(getWeatherUrl(geocode),
        WeatherResult.class);

    return weatherResult.currentWeather.temperature;
  }

  private String getWeatherUrl(Geocode geocode) {
    return String.format(
        "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current_weather=true",
        geocode.latitude, geocode.longitude);
  }

  record WeatherResult(@JsonProperty("current_weather") CurrentWeather currentWeather) {

  }

  record CurrentWeather(String temperature) {

  }

}
