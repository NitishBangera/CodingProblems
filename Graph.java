package graph;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

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

	private void addNode(Node<T> parent, Node<T> child) {
		parent.addChild(child);
		nodes.put(child.getData(), child);
	}

	public void addNode(T parentData, T childData) {
		Node<T> parent = nodes.get(parentData);
		if (parent != null) {
			Node<T> child = nodes.get(childData);
			if (child == null) {
				child = new Node<T>(childData);
				addNode(parent, child);
			} else {
				if (parent.isChild(child)) {
					System.out.println("Child already added to parent");
				} else {
					addNode(parent, child);
				}
			}
		} else {
			System.out.println("No parent present");
		}
	}

	private void deleteNode(Node<T> node) {
		Queue<Node<T>> queue = new ArrayDeque<>();
		queue.add(node);
		boolean parentCheck = false;
		while (!queue.isEmpty()) {
			Node<T> currentNode = queue.remove();
			boolean delete = true;
			for (Node<T> parent : currentNode.getParents()) {
				if (parentCheck && nodes.containsKey(parent.getData())) {
					delete = false;
				} else {
					parent.removeChild(currentNode);
				}
			}

			if (delete) {
				nodes.remove(currentNode.getData());
				queue.addAll(currentNode.getChildren());
			}
			parentCheck = true;
		}
	}

	public void deleteNode(T data) {
		Node<T> node = nodes.get(data);
		if (node != null) {
			if (node == root) {
				root = null;
				nodes.clear();
			} else {
				deleteNode(node);
			}
		} else {
			System.out.println("No node present in graph");
		}
	}

	public static void main(String... args) {
		Graph<String> graph = new Graph<>("1");
		Node<String> root = new Node<>("A");
		graph.addRoot(root);
		graph.addNode("A", "B");
		graph.addNode("B", "C");
		graph.addNode("B", "D");
		graph.addNode("C", "E");
		graph.addNode("D", "E");
		graph.addNode("E", "F");
		System.out.println(graph.getId());
		graph.deleteNode("C");
		System.out.println(graph.getId());
	}
}
