package com.crossover.trial.weather.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.crossover.trial.weather.exception.WeatherException;
import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.model.DataPointType;

@Service
public class AirportService {
	private final static Logger LOGGER = Logger.getLogger(AirportService.class);

	/**
	 * Mapping of all Airport data and its atmospheric information.
	 */
	private Map<String, ImmutablePair<AirportData, AtmosphericInformation>> airportInformation = new ConcurrentHashMap<>();

	/**
	 * Internal performance counter to better understand most requested
	 * information, this map can be improved but for now provides the basis for
	 * future performance optimizations. Due to the stateless deployment
	 * architecture we don't want to write this to disk, but will pull it off
	 * using a REST request and aggregate with other performance metrics
	 * {@link #ping()}
	 */
	private Map<AirportData, Integer> requestFrequency = new ConcurrentHashMap<>();

	private Map<Double, Integer> radiusFreq = new ConcurrentHashMap<>();

	/** earth radius in KM */
	private static final double R = 6372.8;

	/**
	 * Add a new known airport to our list.
	 *
	 * @param iataCode
	 *            3 letter code
	 * @param latitude
	 *            in degrees
	 * @param longitude
	 *            in degrees
	 *
	 * @return the added airport
	 */
	public AirportData addAirport(String iataCode, double latitude, double longitude) {
		AirportData ad = new AirportData();
		ad.setIata(iataCode);
		ad.setLatitude(latitude);
		ad.setLongitude(longitude);
		AtmosphericInformation ai = new AtmosphericInformation();

		airportInformation.put(iataCode, ImmutablePair.of(ad, ai));
		return ad;
	}

	/**
	 * Delete the aiport information.
	 * 
	 * @param iataCode
	 *            3 letter code
	 */
	public void deleteAirport(String iataCode) {
		airportInformation.remove(iataCode);
	}

	/**
	 * Getting all airports.
	 * 
	 * @return Set of airport iata.
	 */
	public Set<String> getAirports() {
		return airportInformation.values().stream().map(ImmutablePair::getLeft)
				.map(AirportData::getIata).collect(Collectors.toSet());
	}

	/**
	 * Given an iataCode find the airport data
	 *
	 * @param iataCode
	 *            as a string
	 * @return airport data or null if not found
	 */
	public AirportData findAirportData(String iataCode) {
		if (airportInformation.containsKey(iataCode)) {
			return airportInformation.get(iataCode).getLeft();
		} else {
			return null;
		}
	}

	/**
	 * Given iata code find atmospheric information.
	 * 
	 * @param iataCode
	 *            String
	 * @return AtmosphericInformation or null if not found
	 */
	public AtmosphericInformation findAtmosphericInformation(String iataCode) {
		if (airportInformation.containsKey(iataCode)) {
			return airportInformation.get(iataCode).getRight();
		} else {
			return null;
		}
	}

	/**
	 * Given an iata code and radius
	 * 
	 * @param iata
	 *            String
	 * @param radiusVal
	 *            String
	 * @return List of atmospheric information
	 */
	public List<AtmosphericInformation> getWeather(String iata, String radiusVal) {
		double radius = radiusVal == null || radiusVal.trim().isEmpty() ? 0 : Double.valueOf(radiusVal);
		updateRequestFrequency(iata, radius);
		List<AtmosphericInformation> result = new ArrayList<>();
		if (radius == 0) {
			Optional.ofNullable(findAtmosphericInformation(iata)).ifPresent(result::add);
		} else {
			Optional.ofNullable(findAirportData(iata)).ifPresent(airportData -> {
				List<AtmosphericInformation> filteredEntities = airportInformation.values().stream()
						.filter(airportInfo -> calculateDistance(airportData, airportInfo.getLeft()) <= radius)
						.map(ImmutablePair::getRight).filter(AtmosphericInformation::validate)
						.collect(Collectors.toList());
				result.addAll(filteredEntities);
			});
		}
		return result;
	}

	/**
	 * Records information about how often requests are made
	 *
	 * @param iata
	 *            an iata code
	 * @param radius
	 *            query radius
	 */
	public void updateRequestFrequency(String iata, Double radius) {
		Optional.ofNullable(findAirportData(iata)).ifPresent(airportData -> {
			requestFrequency.put(airportData, requestFrequency.getOrDefault(airportData, 0) + 1);
			radiusFreq.put(radius, radiusFreq.getOrDefault(radius, 0));
		});
	}

	/**
	 * Haversine distance between two airports.
	 *
	 * @param ad1
	 *            airport 1
	 * @param ad2
	 *            airport 2
	 * @return the distance in KM
	 */
	public double calculateDistance(AirportData ad1, AirportData ad2) {
		double deltaLat = Math.toRadians(ad2.getLatitude() - ad1.getLatitude());
		double deltaLon = Math.toRadians(ad2.getLongitude() - ad1.getLongitude());
		double a = Math.pow(Math.sin(deltaLat / 2), 2)
				+ Math.pow(Math.sin(deltaLon / 2), 2) * Math.cos(ad1.getLatitude()) * Math.cos(ad2.getLatitude());
		double c = 2 * Math.asin(Math.sqrt(a));
		return R * c;
	}

	/**
	 * Update the airports weather data with the collected data.
	 *
	 * @param iataCode
	 *            the 3 letter IATA code
	 * @param pointType
	 *            the point type {@link DataPointType}
	 * @param dp
	 *            a datapoint object holding pointType data
	 */
	public void addDataPoint(String iataCode, String pointType, DataPoint dp) {
		Optional.ofNullable(findAtmosphericInformation(iataCode)).ifPresent(ai -> {
			try {
				updateAtmosphericInformation(ai, pointType, dp);
			} catch (WeatherException e) {
				LOGGER.error("Atmospheric data wasn't updated", e);
			}
		});
	}

	/**
	 * update atmospheric information with the given data point for the given
	 * point type
	 *
	 * @param ai
	 *            the atmospheric information object to update
	 * @param pointType
	 *            the data point type as a string
	 * @param dp
	 *            the actual data point
	 */
	public void updateAtmosphericInformation(AtmosphericInformation ai, String pointType, DataPoint dp)
			throws WeatherException {
		final DataPointType dataPointType = DataPointType.valueOf(pointType.toUpperCase());

		switch (dataPointType) {
		case WIND:
			if (dp.getMean() >= 0) {
				ai.setWind(dp);
				ai.setLastUpdateTime(System.currentTimeMillis());
			}
			break;

		case TEMPERATURE:
			if (dp.getMean() >= -50 && dp.getMean() < 100) {
				ai.setTemperature(dp);
				ai.setLastUpdateTime(System.currentTimeMillis());
			}
			break;

		case HUMIDTY:
			if (dp.getMean() >= 0 && dp.getMean() < 100) {
				ai.setHumidity(dp);
				ai.setLastUpdateTime(System.currentTimeMillis());
			}
			break;

		case PRESSURE:
			if (dp.getMean() >= 650 && dp.getMean() < 800) {
				ai.setPressure(dp);
				ai.setLastUpdateTime(System.currentTimeMillis());
			}
			break;

		case CLOUDCOVER:
			if (dp.getMean() >= 0 && dp.getMean() < 100) {
				ai.setCloudCover(dp);
				ai.setLastUpdateTime(System.currentTimeMillis());
			}
			break;

		case PRECIPITATION:
			if (dp.getMean() >= 0 && dp.getMean() < 100) {
				ai.setPrecipitation(dp);
				ai.setLastUpdateTime(System.currentTimeMillis());
			}
			break;

		default:
			throw new WeatherException("Couldn't update atmospheric data");
		}
	}

	public long getWeatherDataSize() {
		return airportInformation.values().stream().map(ImmutablePair::getRight)
				.filter(AtmosphericInformation::validate)
				.filter(airportInfo -> airportInfo.getLastUpdateTime() > System.currentTimeMillis() - 86400000).count();
	}

	public Map<String, Double> getWeatherInformationFrequency() {
		Map<String, Double> freq = new HashMap<>();
		// fraction of queries
		airportInformation.values().stream().map(ImmutablePair::getLeft).forEach(airportData -> {
			double frac = (double) requestFrequency.getOrDefault(airportData, 0) / requestFrequency.size();
			freq.put(airportData.getIata(), frac);
		});
		return freq;
	}

	public int[] getRadiusFrequency() {
		int m = radiusFreq.keySet().stream().max(Double::compare).orElse(1000.0).intValue() + 1;

		int[] hist = new int[m];
		for (Map.Entry<Double, Integer> e : radiusFreq.entrySet()) {
			int i = e.getKey().intValue() % 10;
			hist[i] += e.getValue();
		}
		return hist;
	}
}
