package com.crossover.trial.weather.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.model.AtmosphericInformation;

public class AirportServiceTest {
	private AirportService airportService;
	
	@Before
	public void setUp() throws Exception {
		airportService = new AirportService();
	}
	
	@After
	public void tearDown() {
		airportService = null;
	}

	@Test
	public void testAddDeleteAirport() {
		String iataCode = "TEST";
		airportService.addAirport(iataCode, 1.0, 2.0);
		AirportData airportData = airportService.findAirportData(iataCode);
		assertNotNull(airportData);
		assertEquals(iataCode, airportData.getIata());
		assertEquals(1.0, airportData.getLatitude(), 0.0);
		assertEquals(2.0, airportData.getLongitude(), 0.0);
		AtmosphericInformation atmosphericInformation = 
				airportService.findAtmosphericInformation(iataCode);
		assertNotNull(atmosphericInformation);
		
		airportService.deleteAirport(iataCode);
		assertNull(airportService.findAirportData(iataCode));
		assertNull(airportService.findAtmosphericInformation(iataCode));
	}
	
	@Test
	public void testGetAirports() {
		addDummyData();
		Set<String> airports = airportService.getAirports();
		assertEquals(5, airports.size());
		assertTrue(airports.contains("BOS"));
		assertTrue(airports.contains("EWR"));
		assertTrue(airports.contains("JFK"));
		assertTrue(airports.contains("LGA"));
		assertTrue(airports.contains("MMU"));
	}
	
	@Test
	public void testGetWeather() {
		addDummyData();
		List<AtmosphericInformation> result = airportService.getWeather("JFK", "0");
		assertEquals(1, result.size());
		AtmosphericInformation info = result.get(0);
		assertNotNull(info);
	}
	
	private void addDummyData() {
		airportService.addAirport("BOS", 42.364347, -71.005181);
		airportService.addAirport("EWR", 40.6925, -74.168667);
		airportService.addAirport("JFK", 40.639751, -73.778925);
		airportService.addAirport("LGA", 40.777245, -73.872608);
		airportService.addAirport("MMU", 40.79935, -74.4148747);
	}

}
