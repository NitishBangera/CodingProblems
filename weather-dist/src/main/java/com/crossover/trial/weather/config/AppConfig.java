package com.crossover.trial.weather.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
	@Value("${server.scheme}")
	private String scheme;

	@Value("${server.name}")
	private String name;

	@Value("${server.port}")
	private int port;

	@Value("${airport.data.filename}")
	private String airportDataFilename;

	public String getServerUrl() {
		return scheme + "://" + name + ":" + port + "/";
	}

	public String getAirportDataFilename() {
		return airportDataFilename;
	}
}
