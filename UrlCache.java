/**
* WebEngage Test.
*
* Create a cache of most frequently visited URLs. 
* Whenever a URL is visited, its value in cache gets updated i.e. its rank increases.
* You should be able to perform actions on this cache like, 
* Top 10 visited URLs, Least visited URLs. URL between rank 20-50, etc.
**/

import java.util.*;

public class UrlCache {
	private Map<String, Integer> urlVisits;
	private LinkedList<String> order;
	
	public UrlCache() {
		urlVisits = new HashMap<String, Integer>();
		order = new LinkedList<String>();
	}
	
	public synchronized void add(String url) {
		int counter = 1;
		if (urlVisits.containsKey(url)) {
			counter = urlVisits.get(url);
			urlVisits.put(url, ++counter);
		} else {
			urlVisits.put(url, counter);
		}
		addInOrder(url, counter);
	}
	
	private void addInOrder(String url, int counter) {
		if (counter == 1) {
			order.offer(url);
		} else {
			for (int i = 0; i < order.size(); i++) {
				String val = order.get(i);
				if (counter >= urlVisits.get(val)) {
					order.add(i, url);
					order.removeLastOccurrence(url);
					break;
				}
			}
		}
	}
	
	public void printFrequentlyVisited(int n) {
		System.out.println("Top " + n + " frequently visited");
		for (String url : order) {
			if (n > 0) {
				System.out.println(url);
			}
			n--;
		}
	}
	
	public void printLeastVisited() {
		System.out.println("Leasted visted : " + order.getLast());
	}
	
	public void printFrequentlyVisited(int fromRank, int toRank) {
		System.out.println("Frequently visited between ranks " + fromRank + " and " + toRank);
		if (--fromRank <= order.size()) {
			for (int i = 0; i < order.size(); i++) {
				if (i < fromRank) {
					continue;
				}
				if (--toRank >= 0) {
					System.out.println(order.get(i));
				}
			}
		} else {
			System.out.println("Invalid request");
		}
	}
	
	public static void main(String... args) {
		UrlCache cache = new UrlCache();
		cache.add("google.com");
		cache.add("yahoo.com");
		cache.add("google.com");
		cache.add("rediff.com");
		cache.add("google.com");
		cache.add("rediff.com");
		cache.printFrequentlyVisited(2);
		cache.printLeastVisited();
		cache.printFrequentlyVisited(2, 3);
	}
}
