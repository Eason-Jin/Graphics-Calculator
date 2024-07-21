package nz.ac.auckland.se281.datastructures.stack;

/** Singly linked nodes used in stack. */
public class Node<T> {

  private T val;
  private Node<T> next;

  /**
   * Create a node contructor that takes in its content.
   *
   * @param data the content of this node.
   */
  public Node(T data) {
    this.val = data;
    this.next = null;
  }

  /**
   * Return the content of this node.
   *
   * @return val.
   */
  public T getData() {
    return this.val;
  }

  /**
   * Write content to this node.
   *
   * @param data the content of node.
   */
  public void setData(T data) {
    this.val = data;
  }

  /**
   * Get the next linked node.
   *
   * @return next linked node.
   */
  public Node<T> getNext() {
    return this.next;
  }

  /**
   * Set the next linked node.
   *
   * @param next next node.
   */
  public void setNext(Node<T> next) {
    this.next = next;
  }
}
