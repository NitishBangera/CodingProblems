package graph;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Node<T> {
	private T data;
	private Set<Node<T>> parents;
	private Set<Node<T>> children;

	public Node(T data) {
		this.data = data;
		parents = new LinkedHashSet<>();
		children = new LinkedHashSet<>();
	}

	public T getData() {
		return data;
	}

	public Set<Node<T>> getParents() {
		return Collections.unmodifiableSet(parents);
	}

	public Set<Node<T>> getChildren() {
		return Collections.unmodifiableSet(children);
	}

	public void addParent(Node<T> parent) {
		parents.add(parent);
	}

	public void addChild(Node<T> child) {
		child.addParent(this);
		children.add(child);
	}

	public void removeParent(Node<T> parent) {
		parents.remove(parent);
		parent.removeChild(this);
	}

	public void removeChild(Node<T> child) {
		children.remove(child);
	}

	public boolean isChild(Node<T> child) {
		return children.contains(child);
	}

	public boolean isParent(Node<T> parent) {
		return parents.contains(parent);
	}

	@Override
	public String toString() {
		return "Node [data=" + data.toString() + ", parents=" + parents.size() + ", children=" + children.size() + "]";
	}
}
