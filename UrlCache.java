/**
* WebEngage Test.
*
* Create a cache of most frequently visited URLs. 
* Whenever a URL is visited, its value in cache gets updated i.e. its rank increases.
* You should be able to perform actions on this cache like, 
* Top 10 visited URLs, Least visited URLs. URL between rank 20-50, etc.
**/

import java.util.*;

class CacheStructure<T> {
	private Map<T, Integer> visits;
	private Map<T, Node<T>> dataMap;
	private Node<T> head;
	private Node<T> rear;
	private Node<T> current;
	private int size;
	
	public CacheStructure() {
		visits = new HashMap<T, Integer>();
		dataMap = new HashMap<T, Node<T>>();
	}

	public void add(T data) {
		Node<T> dataVal = new Node<T>(data);
		int counter = 1;
		if (visits.containsKey(data)) {
			counter = visits.get(data);
			++counter;
		}
		visits.put(data, counter);
		
		if (counter == 1) {
			addLast(dataVal);
			dataMap.put(data, dataVal);
		} else {
			dataVal = dataMap.get(data);
			Node<T> val = head;
			while(val != null) {
				if (dataVal.equals(val)) {
					break;
				}
				if (counter >= visits.get(val.getData())) {
					Node<T> dataPrev = dataVal.getPrevious();
					if (dataPrev != null) {
						dataPrev.setNext(dataVal.getNext());
						if (dataVal == rear) {
							rear = dataPrev;
						}
					}
					
					if (val == head) {
						head = dataVal;
					}
					
					Node<T> prev = val.getPrevious();
					dataVal.setPrevious(prev);
					if (prev != null) {
						prev.setNext(dataVal);
					}
					
					dataVal.setNext(val);
					val.setPrevious(dataVal);
					
					break;
				}
				val = val.getNext();
			}
		}
	}
	
	private void addLast(final Node<T> dataVal) {
		if (head == null) {
			head = dataVal;
		} else {
			dataVal.setPrevious(rear);
			rear.setNext(dataVal);
		}
		rear = dataVal;
		current = dataVal;
		size++;
	}
	
	public void print() {
		Node<T> val = head;
		while(val != null) {
			System.out.println(val.getData() + " " + visits.get(val.getData()));
			val = val.getNext();
		}
	}
	
	public void printLeastVisited() {
		System.out.println("Leasted visted : " + rear.getData());
	}
	
	public void printFrequentlyVisited(int n) {
		System.out.println("Top " + n + " frequently visited");
		Node<T> val = head;
		while(val != null) {
			if (n > 0) {
				System.out.println(val.getData());
				val = val.getNext();
			} else {
				break;
			}
			n--;
		}
	}
	
	public void printFrequentlyVisited(int fromRank, int toRank) {
		System.out.println("Frequently visited between ranks " + fromRank + " and " + toRank);
		if (--fromRank <= size) {
			Node<T> val = head;
			int i = 0;
			while(val != null) {
				if (i >= fromRank) {
					if (--toRank >= 0) {
						System.out.println(val.getData());
					} else {
						break;
					}
				}
				val = val.getNext();
				i++;
			}
		} else {
			System.out.println("Invalid request");
		}
	}
}

class Node<T> {
	private T data;
	private Node<T> previous;
	private Node<T> next;
	
	public Node(T data) {
		if (data != null) {
			this.data = data;
		} else {
			throw new RuntimeException("Null not allowed");
		}
	}
	
	public T getData() {
		return data;
	}
	
	public Node<T> getPrevious() {
		return previous;
	}
	
	public void setPrevious(Node<T> previous) {
		this.previous = previous;
	}
	
	public Node<T> getNext() {
		return next;
	}
	
	public void setNext(Node<T> next) {
		this.next = next;
	}
	
	@Override
	public int hashCode() {
		return data.hashCode();
	}
	
	@Override
	public boolean equals(Object that) {
		if (that == null) return false;
		if (that == this) return true;
		if (!(that instanceof Node))return false;
		Node thatVal = (Node) that;
		return this.data.equals(thatVal.getData());
	}
}

public class UrlCache {	
	public static void main(String... args) {
		CacheStructure<String> struct = new CacheStructure<String>();
		struct.add("google.com");
		struct.add("yahoo.com");
		struct.add("google.com");
		struct.add("rediff.com");
		struct.add("google.com");
		struct.add("rediff.com");
		struct.print();
		struct.printLeastVisited();
		struct.printFrequentlyVisited(2);
		struct.printFrequentlyVisited(2, 3);
		struct.add("yahoo.com");
		struct.add("yahoo.com");
		struct.add("yahoo.com");
		struct.add("test.com");
		struct.print();
		struct.printLeastVisited();
		struct.printFrequentlyVisited(3);
		struct.printFrequentlyVisited(1, 3);
	}
}
