package com.betrybe.weatherreport;

import com.betrybe.weatherreport.interfaces.WeatherClient;
import com.betrybe.weatherreport.interfaces.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Javadoc.
 */
@Component
public class WeatherServiceImp implements WeatherService {

  private final WeatherClient weatherClient;

  @Autowired
  public WeatherServiceImp(WeatherClient weatherClient) {
    this.weatherClient = weatherClient;
  }

  @Override
  public String getWeatherReport(String city) {
    String weather = weatherClient.getWeather(city);
    return "O clima é: " + weather;
  }
}
