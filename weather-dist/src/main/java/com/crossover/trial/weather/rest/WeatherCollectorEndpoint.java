package com.crossover.trial.weather.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.model.DataPointType;

/**
 * The interface shared to airport weather collection systems.
 *
 * @author code test administartor
 */
public interface WeatherCollectorEndpoint {

	/**
	 * A liveliness check for the collection endpoint.
	 *
	 * @return 1 if the endpoint is alive functioning, 0 otherwise
	 */
	@GetMapping("ping")
	ResponseEntity<?> ping();

	/**
	 * Update the airports atmospheric information for a particular pointType
	 * with json formatted data point information.
	 *
	 * @param iataCode
	 *            the 3 letter airport code
	 * @param pointType
	 *            the point type, {@link DataPointType} for a complete list
	 * @param datapointJson
	 *            a json dict containing mean, first, second, thrid and count
	 *            keys
	 *
	 * @return HTTP Response code
	 */
	@PostMapping("weather/{iata}/{pointType}")
	ResponseEntity<?> updateWeather(@PathVariable("iata") String iataCode, @PathVariable("pointType") String pointType,
			@RequestBody String datapointJson);

	/**
	 * Return a list of known airports as a json formatted list
	 *
	 * @return HTTP Response code and a json formatted list of IATA codes
	 */
	@GetMapping(value = "airports", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getAirports();

	/**
	 * Retrieve airport data, including latitude and longitude for a particular
	 * airport
	 *
	 * @param iata
	 *            the 3 letter airport code
	 * @return an HTTP Response with a json representation of
	 *         {@link AirportData}
	 */
	@GetMapping(value = "airport/{iata}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getAirport(@PathVariable("iata") String iata);

	/**
	 * Add a new airport to the known airport list.
	 *
	 * @param iata
	 *            the 3 letter airport code of the new airport
	 * @param latString
	 *            the airport's latitude in degrees as a string [-90, 90]
	 * @param longString
	 *            the airport's longitude in degrees as a string [-180, 180]
	 * @return HTTP Response code for the add operation
	 */
	@PostMapping("airport/{iata}/{lat}/{long}")
	ResponseEntity<?> addAirport(@PathVariable("iata") String iata, @PathVariable("lat") String latString,
			@PathVariable("long") String longString);

	/**
	 * Remove an airport from the known airport list
	 *
	 * @param iata
	 *            the 3 letter airport code
	 * @return HTTP Repsonse code for the delete operation
	 */
	@DeleteMapping("airport/{iata}")
	ResponseEntity<?> deleteAirport(@PathVariable("iata") String iata);

	@GetMapping("exit")
	ResponseEntity<?> exit();
}
