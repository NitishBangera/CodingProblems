package com.crossover.trial.weather.loader;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.crossover.trial.weather.config.AppConfig;
import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.parser.FileParser;

/**
 * A simple airport loader which reads a file from disk and sends entries to the
 * webservice
 *
 * TODO: Implement the Airport Loader
 * 
 * @author code test administrator
 */
@Component
public class AirportLoader {
	private AppConfig appConfig;
	private RestTemplate restTemplate;
	private ResourceLoader resourceLoader;

	@Autowired
	public AirportLoader(AppConfig appConfig, RestTemplate restTemplate, ResourceLoader resourceLoader) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
		this.resourceLoader = resourceLoader;
	}

	public void load() throws IOException {
		Resource airportResource = resourceLoader.getResource("classpath:" + appConfig.getAirportDataFilename());
		List<AirportData> airportDatas = FileParser.parse(airportResource.getInputStream(), AirportData.class);
		airportDatas.forEach(airportData -> {
			String postUrl = String.format("%scollect/airport/%s/%s/%s", appConfig.getServerUrl(),
					airportData.getIata(), airportData.getLatitude(), airportData.getLongitude());
			restTemplate.postForEntity(postUrl, "", String.class);
		});
	}
}
