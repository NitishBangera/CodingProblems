import java.util.Queue;
import java.util.Deque;
import java.util.LinkedList;

class Node {
  private int data;
  private Node left;
  private Node right;

  public Node(final int dataArg) {
    data = dataArg;
  }

  public int getData() {
    return data;
  }

  public void setLeft(Node node) {
    left = node;
  }

  public void setRight(Node node) {
    right = node;
  }

  public Node getLeft() {
    return left;
  }

  public Node getRight() {
    return right;
  }
}

public class BinaryTreeTraversal {
  public static Node constructCompleteBinaryTree(Queue<Integer> data) {
    Node root = null;
    Queue<Node> queue = new LinkedList<Node>();
    Integer value = data.poll();
    if (null != value) {
      root = new Node(value);
      queue.add(root);
      while (!queue.isEmpty()) {
        Node parent = queue.poll();
        value = data.poll();
        if (null != value) {
          Node leftChild = new Node(value);
          queue.add(leftChild);
          parent.setLeft(leftChild);
        }

        value = data.poll();
        if (null != value) {
          Node rightChild = new Node(value);
          queue.add(rightChild);
          parent.setRight(rightChild);
        }
      }
    }
    return root;
  }

  public static void inOrder(Node node) {
    if (null != node) {
      inOrder(node.getLeft());
      System.out.print(node.getData() + " ");
      inOrder(node.getRight());
    }
  }

  public static void spiralOrder(Node node) {
    System.out.println("\nSpiral Tree");
    if (null != node) {
      Deque<Node> stack1 = new LinkedList<Node>();
      Deque<Node> stack2 = new LinkedList<Node>();

      stack1.addFirst(node);

      while(!(stack1.isEmpty() && stack2.isEmpty())) {
        Node val = null;
        while(!stack1.isEmpty()) {
          val = stack1.removeFirst();
          if (null != val) {
            System.out.print(val.getData() + " ");
            Node leftChild = val.getLeft();
            if (null != leftChild) {
              stack2.addFirst(leftChild);
            }

            Node rightChild = val.getRight();
            if (null != rightChild) {
              stack2.addFirst(rightChild);
            }
          }
        }

        while(!stack2.isEmpty()) {
          val = stack2.removeFirst();
          if (null != val) {
            System.out.print(val.getData() + " ");
            Node rightChild = val.getRight();
            if (null != rightChild) {
              stack1.addFirst(rightChild);
            }
            Node leftChild = val.getLeft();
            if (null != leftChild) {
              stack1.addFirst(leftChild);
            }
          }
        }
      }
    }
  }

  public static void breadthFirst(Node node) {
    System.out.println("\nBFS");
    if (null != node) {
      Queue<Node> queue = new LinkedList<Node>();
      queue.add(node);

      while(!queue.isEmpty()) {
        Node val = queue.poll();
        System.out.print(val.getData() + " ");

        Node leftChild = val.getLeft();
        if (null != leftChild) {
          queue.add(leftChild);
        }

        Node rightChild = val.getRight();
        if (null != rightChild) {
          queue.add(rightChild);
        }
      }
    }
  }

  public static void main(String... args) {
    int n = Integer.parseInt(args[0]);
    Queue<Integer> data = new LinkedList<Integer>();
    for (int i = 1; i <= n; i++) {
      data.add(i);
    }
    Node root = constructCompleteBinaryTree(data);
    System.out.println("Inorder");
    inOrder(root);
    spiralOrder(root);
    breadthFirst(root);
  }
}
