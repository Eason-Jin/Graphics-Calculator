package nz.ac.auckland.se281.datastructures.queue;

/** Doubly linked nodes used in queue. */
public class Node<T> {

  private T val;
  private Node<T> prev;
  private Node<T> next;

  /**
   * Create a node contructor that takes in its content.
   *
   * @param data the content of this node.
   */
  public Node(T data) {
    this.val = data;
    this.next = null;
    this.prev = null;
  }

  /**
   * Return the content of this node.
   *
   * @return val.
   */
  public T getData() {
    return val;
  }

  /**
   * Get the previous linked node.
   *
   * @return prev node.
   */
  public Node<T> getPrev() {
    return prev;
  }

  /**
   * Write content to this node.
   *
   * @param data the content of node.
   */
  public void setData(T data) {
    val = data;
  }

  /**
   * Establish connection between this node and the previous node.
   *
   * @param prev previous node.
   */
  public void setPrev(Node<T> prev) {
    this.prev = prev;
  }

  /**
   * Get the next linked node.
   *
   * @return next linked node.
   */
  public Node<T> getNext() {
    return next;
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
