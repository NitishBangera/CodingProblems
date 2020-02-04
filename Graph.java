package graph;

import java.util.HashMap;
import java.util.Map;

public class Graph<T> {
	private String id;
	private Node<T> root;
	private Map<T, Node<T>> nodes = new HashMap<>();
	
	public Graph(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void addRoot(Node<T> node) {
		this.root = node;
		nodes.clear();
		nodes.put(root.getData(), root);
	}
	
	public void addNode(T parentData, T childData) {
		Node<T> parent = nodes.get(parentData);
		if (parent != null) {
			Node<T> child = nodes.get(childData);
			if (child == null) {
				child = new Node<T>(childData);
				parent.addOutgoingNode(child);
				child.addIncomingNode(parent);
			}
		}
	}
}
