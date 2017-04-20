package com.crossover.trial.weather.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.service.AirportService;

/**
 * The Weather App REST endpoint allows clients to query, update and check
 * health stats. Currently, all data is held in memory. The end point deploys to
 * a single container
 *
 * @author code test administrator
 */
@RestController
@RequestMapping(value = "query")
public class RestWeatherQueryEndpoint implements WeatherQueryEndpoint {
	private final AirportService airportService;

	@Autowired
	public RestWeatherQueryEndpoint(final AirportService airportService) {
		this.airportService = airportService;
	}

	/**
	 * Retrieve service health including total size of valid data points and
	 * request frequency information.
	 *
	 * @return health stats for the service as a string
	 */
	@Override
	public ResponseEntity<Map<String, Object>> ping() {
		Map<String, Object> retval = new HashMap<>();
		retval.put("datasize", airportService.getWeatherDataSize());
		retval.put("iata_freq", airportService.getWeatherInformationFrequency());
		retval.put("radius_freq", airportService.getRadiusFrequency());
		return ResponseEntity.ok(retval);
	}

	/**
	 * Given a query in json format {'iata': CODE, 'radius': km} extracts the
	 * requested airport information and return a list of matching atmosphere
	 * information.
	 *
	 * @param iata
	 *            the iataCode
	 * @param radiusString
	 *            the radius in km
	 *
	 * @return a list of atmospheric information
	 */
	@Override
	public ResponseEntity<List<AtmosphericInformation>> weather(@PathVariable("iata") String iata, 
			@PathVariable("radius") String radiusString) {
		return ResponseEntity.ok(airportService.getWeather(iata, radiusString));
	}
}
