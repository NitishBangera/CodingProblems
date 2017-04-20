package com.crossover.trial.weather.rest;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.service.AirportService;
import com.google.gson.Gson;

public class WeatherEndpointTest {
	private AirportService airportService;

	private WeatherQueryEndpoint _query;

	private WeatherCollectorEndpoint _update;

	private Gson _gson = new Gson();

	private DataPoint _dp;

	@Before
	public void setUp() throws Exception {
		airportService = new AirportService();
		airportService.addAirport("BOS", 42.364347, -71.005181);
		airportService.addAirport("EWR", 40.6925, -74.168667);
		airportService.addAirport("JFK", 40.639751, -73.778925);
		airportService.addAirport("LGA", 40.777245, -73.872608);
		airportService.addAirport("MMU", 40.79935, -74.4148747);
		
		_query = new RestWeatherQueryEndpoint(airportService);
		_update = new RestWeatherCollectorEndpoint(airportService);
		_gson = new Gson();
		_dp = new DataPoint.Builder().withCount(10).withFirst(10).withMedian(20).withLast(30).withMean(22).build();
		_update.updateWeather("BOS", "wind", _gson.toJson(_dp));
		ResponseEntity<?> response = _query.weather("BOS", "0");
		response.getBody();
	}
	
	@After
	public void tearDown() {
		airportService = null;
		_query = null;
		_update = null;
		_gson = null;
		_dp = null;
	}

	@Test
	public void testPing() throws Exception {
		ResponseEntity<Map<String, Object>> response = _query.ping();
		Map<String, Object> ping = response.getBody();
		assertEquals((long) 1, ping.get("datasize"));
		Map<String, Double> iataFreq = (Map<String, Double>) ping.get("iata_freq");
		assertEquals(5, iataFreq.entrySet().size());
	}

	@Test
	public void testGet() throws Exception {
		List<AtmosphericInformation> ais = (List<AtmosphericInformation>) _query.weather("BOS", "0").getBody();
		assertEquals(ais.get(0).getWind(), _dp);
	}

	@Test
	public void testGetNearby() throws Exception {
		// check datasize response
		_update.updateWeather("JFK", "wind", _gson.toJson(_dp));
		_dp.setMean(40);
		_update.updateWeather("EWR", "wind", _gson.toJson(_dp));
		_dp.setMean(30);
		_update.updateWeather("LGA", "wind", _gson.toJson(_dp));

		List<AtmosphericInformation> ais = (List<AtmosphericInformation>) _query.weather("JFK", "200").getBody();
		assertEquals(3, ais.size());
	}

	@Test
	public void testUpdate() throws Exception {

		DataPoint windDp = new DataPoint.Builder().withCount(10).withFirst(10).withMedian(20).withLast(30).withMean(22)
				.build();
		_update.updateWeather("BOS", "wind", _gson.toJson(windDp));
		_query.weather("BOS", "0");

		Map<String, Object> ping = _query.ping().getBody();
		assertEquals((long) 1, ping.get("datasize"));

		DataPoint cloudCoverDp = new DataPoint.Builder().withCount(4).withFirst(10).withMedian(60).withLast(100)
				.withMean(50).build();
		_update.updateWeather("BOS", "cloudcover", _gson.toJson(cloudCoverDp));

		List<AtmosphericInformation> ais = (List<AtmosphericInformation>) _query.weather("BOS", "0").getBody();
		assertEquals(ais.get(0).getWind(), windDp);
		assertEquals(ais.get(0).getCloudCover(), cloudCoverDp);
	}

}
