package com.betrybe.weatherreport;

import com.betrybe.weatherreport.interfaces.WeatherService;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Weather Report main class.
 */
@Component
public class WeatherReportMain {

  private final ApplicationContext context;

  // Bora.
  @Autowired
  public WeatherReportMain(ApplicationContext context) {
    this.context = context;
  }

  /**
   * Main method that is called to start the application.
   */
  public void run(String[] args) {
    WeatherService weatherService = context.getBean(WeatherService.class);

    System.out.print("Digite o nome da cidade: ");
    Scanner scanner = new Scanner(System.in);

    String city = scanner.nextLine();
    scanner.close();

    System.out.println(weatherService.getWeatherReport(city));
  }
}
