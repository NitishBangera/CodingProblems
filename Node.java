package graph;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Node<T> {
	private T data;
	private Set<Node<T>> incoming;
	private Set<Node<T>> outgoing;
	
	public Node(T data) {
		this.data = data;
		incoming = new LinkedHashSet<>();
		outgoing = new LinkedHashSet<>();
	}
	
	public T getData() {
		return data;
	}
	
	public Set<Node<T>> getIncoming() {
		return Collections.unmodifiableSet(incoming);
	}
	
	public void addIncomingNode(Node<T> node) {
		incoming.add(node);
	}
	
	public Set<Node<T>> getOutgoing() {
		return Collections.unmodifiableSet(outgoing);
	}
	
	public void addOutgoingNode(Node<T> node) {
		outgoing.add(node);
	}
}
