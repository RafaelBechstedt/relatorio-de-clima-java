package com.betrybe.weatherreport;

import com.betrybe.weatherreport.interfaces.WeatherClient;
import org.springframework.stereotype.Component;

/**
 * Javadoc.
 */
@Component
public class WeatherProviderClient implements WeatherClient {

  @Override
  public String getWeather(String city) {
    return "tempinho bom!";
  }
}
