package com.crossover.trial.weather;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.crossover.trial.weather.config.AppConfig;
import com.crossover.trial.weather.loader.AirportLoader;

@SpringBootApplication
public class WeatherServer {
	private final static Logger LOGGER = Logger.getLogger(WeatherServer.class);

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		try {			
			ConfigurableApplicationContext appContext = SpringApplication.run(WeatherServer.class, args);
			AppConfig appConfig = appContext.getBean(AppConfig.class);
			final String baseUrl = appConfig.getServerUrl();
			System.out.println("Starting Weather App local testing server: " + baseUrl);
			AirportLoader airportLoader = appContext.getBean(AirportLoader.class);
			airportLoader.load();
			// the autograder waits for this output before running automated
			// tests, please don't remove it
			System.out.printf("Weather Server started.\n url=%s\n", baseUrl);
			// blocks until the process is terminated
			Thread.currentThread().join();
			appContext.stop();
		} catch (IOException | InterruptedException ex) {
			LOGGER.error(ex);
		}
	}
}
