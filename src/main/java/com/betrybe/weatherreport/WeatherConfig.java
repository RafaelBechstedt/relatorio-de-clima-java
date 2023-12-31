package com.betrybe.weatherreport;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Javadoc.
 */
@Configuration
public class WeatherConfig {

  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }
}