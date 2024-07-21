package nz.ac.auckland.se281.datastructures.queue;

/** A queue using doubly linked list. */
public class Queue<T> {

  private Node<T> head;
  private Node<T> tail;
  private int size = 0;

  public Queue() {
    head = null;
    tail = null;
  }

  /**
   * Put an element at the end of the queue.
   *
   * @param data the element to queue.
   */
  public void enqueue(T data) {
    // Create a new temp node
    Node<T> temp = new Node<T>(data);

    // If the queue is empty, set head and tail to temp
    if (isEmpty()) {
      head = temp;
      tail = temp;
    } else {
      // Otherwise link the tail to the new node and move the tail
      tail.setNext(temp);
      temp.setPrev(tail);
      tail = temp;
    }

    size++;
  }

  /**
   * Remove the element at the start of the queue.
   *
   * @return the element at the start of the queue.
   */
  public T dequeue() {
    T temp = head.getData();

    // Queue of size 1
    if (head == tail) {
      head = null;
      tail = null;
    } else {
      // Move the head to the next element and delink the previous head
      head = head.getNext();
      head.setPrev(null);
    }

    size--;
    return temp;
  }

  /**
   * Get the first element without removing it.
   *
   * @return the first element of the queue.
   */
  public T peek() {
    return head.getData();
  }

  /**
   * Return the size of the queue.
   *
   * @return size.
   */
  public int size() {
    return size;
  }

  /**
   * Check if the queue is empty.
   *
   * @return boolean, whether the queue is empty.
   */
  public boolean isEmpty() {
    return size == 0;
  }
}
