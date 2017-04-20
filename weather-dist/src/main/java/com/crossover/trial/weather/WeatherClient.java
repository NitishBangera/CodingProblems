package com.crossover.trial.weather;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.crossover.trial.weather.model.DataPoint;

/**
 * A reference implementation for the weather client. Consumers of the REST API can look at WeatherClient
 * to understand API semantics. This existing client populates the REST endpoint with dummy data useful for
 * testing.
 *
 * @author code test administrator
 */
public class WeatherClient {
    private static final String BASE_URI = "http://localhost:9090";
    
    private String queryUri;
    
    private String collectUri;
    
    private RestTemplate restTemplate;

    public WeatherClient() {
    	restTemplate = new RestTemplate();
        queryUri = BASE_URI + "/query";
        collectUri = BASE_URI + "/collect";
    }

    public void pingCollect() {
    	String url = collectUri + "/ping";
    	ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.print("collect.ping: " + response.getBody() + "\n");
    }

    public void query(String iata) {
    	String url = queryUri + "/weather/" + iata + "/0";
    	ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println("query." + iata + ".0: " + response.getBody());
    }

    public void pingQuery() {
    	String url = queryUri + "/ping";
    	ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println("query.ping: " + response.getBody());
    }

    public void populate(String pointType, int first, int last, int mean, int median, int count) {
    	String url = collectUri + "/weather/BOS/" + pointType;
        DataPoint dp = new DataPoint.Builder()
                .withFirst(first).withLast(last).withMean(mean).withMedian(median).withCount(count)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        HttpEntity<DataPoint> entity = new HttpEntity<DataPoint>(dp, headers);
        restTemplate.postForEntity(url, entity, String.class);
    }

    public void exit() {
    	String url = collectUri + "/exit";
        try {
        	restTemplate.getForEntity(url, String.class);
        } catch (Throwable t) {
            // swallow
        }
    }

    public static void main(String[] args) {
        WeatherClient wc = new WeatherClient();
        wc.pingCollect();
        wc.populate("wind", 0, 10, 6, 4, 20);

        wc.query("BOS");
        wc.query("JFK");
        wc.query("EWR");
        wc.query("LGA");
        wc.query("MMU");

        wc.pingQuery();
        wc.exit();
        System.out.print("complete");
        System.exit(0);
    }
}
