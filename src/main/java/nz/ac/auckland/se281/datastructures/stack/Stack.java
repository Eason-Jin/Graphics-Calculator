package nz.ac.auckland.se281.datastructures.stack;

/** A stack using singly linked list. */
public class Stack<T> {

  private Node<T> top;
  private int size = 0;

  public Stack() {
    top = null;
  }

  /**
   * Put an element at the top of the stack.
   *
   * @param data the element to stack.
   */
  public void push(T data) {
    // Create a new temp node
    Node<T> temp = new Node<T>(data);

    // Link top with temp and move top
    temp.setNext(top);
    top = temp;

    size++;
  }

  /**
   * Remove the element at the top of the stack.
   *
   * @return the element at the top of the stack.
   */
  public T pop() {
    T temp = top.getData();

    // Stack of size 1
    if (size == 1) {
      top = null;
    } else {
      // Otherwise move top and delink
      top = top.getNext();
    }
    size--;
    return temp;
  }

  /**
   * Get the first element without removing it.
   *
   * @return the first element of the stack.
   */
  public T peek() {
    return top.getData();
  }

  /**
   * Return the size of the stack.
   *
   * @return size.
   */
  public int size() {
    return size;
  }

  /**
   * Check if the stack is empty.
   *
   * @return boolean, whether the stack is empty.
   */
  public boolean isEmpty() {
    return size == 0;
  }
}
