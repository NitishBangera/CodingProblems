package com.crossover.trial.weather.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.crossover.trial.weather.model.AtmosphericInformation;

/**
 * The query only API for the Weather Server App. This API is made available to the public internet.
 *
 * @author code test adminsitrator
 */
public interface WeatherQueryEndpoint {

    /**
     * Retrieve health and status information for the the query api. Returns information about how the number
     * of datapoints currently held in memory, the frequency of requests for each IATA code and the frequency of
     * requests for each radius.
     *
     * @return a JSON formatted dict with health information.
     */
    @GetMapping("ping")
    ResponseEntity<Map<String, Object>> ping();

    /**
     * Retrieve the most up to date atmospheric information from the given airport and other airports in the given
     * radius.
     *
     * @param iata the three letter airport code
     * @param radiusString the radius, in km, from which to collect weather data
     *
     * @return an HTTP Response and a list of {@link AtmosphericInformation} from the requested airport and
     * airports in the given radius
     */
    @GetMapping(value = "weather/{iata}/{radius}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<AtmosphericInformation>> weather(@PathVariable("iata") String iata, 
    		@PathVariable("radius") String radiusString);
}
