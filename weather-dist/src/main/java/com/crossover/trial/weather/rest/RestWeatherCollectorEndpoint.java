package com.crossover.trial.weather.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.service.AirportService;
import com.google.gson.Gson;

/**
 * A REST implementation of the WeatherCollector API. Accessible only to airport
 * weather collection sites via secure VPN.
 *
 * @author code test administrator
 */
@RestController
@RequestMapping(value = "collect")
public class RestWeatherCollectorEndpoint implements WeatherCollectorEndpoint {
	/**
	 * Shared gson json to object factory
	 */
	public final static Gson gson = new Gson();

	private final AirportService airportService;

	public RestWeatherCollectorEndpoint(final AirportService airportService) {
		this.airportService = airportService;
	}

	@Override
	public ResponseEntity<?> ping() {
		return ResponseEntity.ok("ready");
	}

	@Override
	public ResponseEntity<?> updateWeather(@PathVariable("iata") String iataCode,
			@PathVariable("pointType") String pointType, @RequestBody String datapointJson) {
		airportService.addDataPoint(iataCode, pointType, gson.fromJson(datapointJson, DataPoint.class));
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<?> getAirports() {
		return ResponseEntity.ok(airportService.getAirports());
	}

	@Override
	public ResponseEntity<?> getAirport(@PathVariable("iata") String iata) {
		return ResponseEntity.ok(airportService.findAirportData(iata));
	}

	@Override
	public ResponseEntity<?> addAirport(@PathVariable("iata") String iata, @PathVariable("lat") String latString,
			@PathVariable("long") String longString) {
		airportService.addAirport(iata, Double.valueOf(latString), Double.valueOf(longString));
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<?> deleteAirport(@PathVariable("iata") String iata) {
		airportService.deleteAirport(iata);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<?> exit() {
		System.exit(0);
		return ResponseEntity.noContent().build();
	}
}
