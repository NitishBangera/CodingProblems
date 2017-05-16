import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Collections;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
* Statement Campus Test : Paginated Movie titles for the UI.
*
* A module to get values in batches and then pass it on to the front.
* Enhancement: Can stream the values directly using BlockingQueue and
* in multiple threads using ExecutorServices.
**/
public class SubStringSearch {
	public static void main(String... args) {
		SubStringSearch search = new SubStringSearch();
		for (String val : search.getVal("spiderman")) {
			System.out.println(val);
		}
	}

	public String[] getVal(String subStr) {
		String urlFormat = "https://jsonmock.hackerrank.com/api/movies/search/?Title=%s&page=%s";
		HttpURLConnection conn = null;
		JSONParser parser = new JSONParser();
		List<String> movieTitles = new LinkedList<String>();
		boolean hasNext = true;
		int retry = 0;
		final int maxRetry = 3;
		int pageVal = 1;
		while(retry < maxRetry) {
			try {
				URL obj = new URL(String.format(urlFormat, subStr, pageVal));
				conn = (HttpURLConnection) obj.openConnection();
				conn.setRequestMethod("GET");
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(conn.getInputStream()));
					JSONArray data = (JSONArray) jsonObject.get("data");
					Iterator<JSONObject> iterator = data.iterator();
				    while (iterator.hasNext()) {
				    	JSONObject movie = iterator.next();
				    	String title = (String) movie.get("Title");
				    	movieTitles.add(title);				    					    	
				    }				
					long page = Long.parseLong((String) jsonObject.get("page"));
					long total_pages = (Long) jsonObject.get("total_pages");
					
					if (page < total_pages) {
						pageVal++;
					} else {
						break;
					}
				}
				retry = 0;
			} catch(Exception e) {
				System.out.println(e);
				retry++;
			} finally {
				if (conn != null) {
					conn.disconnect();			
				}		
			}
		}
		Collections.sort(movieTitles);
		return movieTitles.toArray(new String[movieTitles.size()]);		
	}
}
