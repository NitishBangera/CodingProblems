package org.example;

import java.util.LinkedList;
import java.util.List;

public class NodeIterator {
    private Node start;

    private Node end;

    public NodeIterator(List<Integer> values) {
        Node prev = null;
        for (Integer value : values) {
            var current = new Node(value);
            if (start == null) {
                start = current;
                end = current;
                current.setPrev(null);
            } else {
                end = current;
                current.setPrev(prev);
            }
            prev = current;
        }
    }

    // 1 -> 2 -> 3 -> 7 -> 9 -> 11 = Output: 12,11,10
    // 1 -> 2 -> 3 -> 7 -> 9 = Output: 12,11,3
    // Start -> End ->
    public void calculateSumEdge() {
        System.out.println("---------------");
        var currentI = start;
        var currentJ = end;

        while (currentI != null && currentJ != null && currentI != currentJ && currentI.getPrev() != currentJ) {
            System.out.println(currentI.getData() + currentJ.getData());

            // Move forward
            currentI = currentI.getNext();

            // Move backward
            currentJ = currentJ.getPrev();
        }

        // If pointers meet in the middle, print the middle node's data
        if (currentI == currentJ) {
            System.out.println(currentI.getData());
        }

        System.out.println("---------------");
    }

    public static void main(String[] args) {
        var listValues = new LinkedList<Integer>();
        listValues.add(1);
        listValues.add(2);
        listValues.add(3);
        listValues.add(4);
        listValues.add(7);
        listValues.add(9);
        listValues.add(11);
        var iterator = new NodeIterator(listValues);
        iterator.calculateSumEdge();
    }

    public static class Node {
        private NodeIterator.Node prev;
        private final int data;
        private NodeIterator.Node next;

        public Node(int data) {
            this.data = data;
        }

        public NodeIterator.Node getPrev() {
            return prev;
        }

        public void setPrev(NodeIterator.Node prev) {
            this.prev = prev;
            if (this.prev != null) {
                this.prev.setNext(this);
            }
        }

        public int getData() {
            return data;
        }

        public NodeIterator.Node getNext() {
            return next;
        }

        public void setNext(NodeIterator.Node next) {
            this.next = next;
        }
    }
}
