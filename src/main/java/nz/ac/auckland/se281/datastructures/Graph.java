package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import nz.ac.auckland.se281.datastructures.queue.Queue;
import nz.ac.auckland.se281.datastructures.stack.Stack;

/**
 * A graph that is composed of a set of verticies and edges.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Graph<T extends Comparable<T>> {
  private Set<T> vertices;
  private Set<Edge<T>> edges;

  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.vertices = verticies;
    this.edges = edges;
  }

  /**
   * Return the root(s) of this graph.
   *
   * @return a set of root(s) of the graph.
   */
  public Set<T> getRoots() {
    List<T> rootsList = new ArrayList<T>();
    SortedSet<T> roots = new TreeSet<T>();
    Set<T> equivalenceClass;
    int inDegree;
    int outDegree;

    // Loop through vertices
    for (T vertex : vertices) {
      equivalenceClass = new HashSet<T>();
      inDegree = 0;
      outDegree = 0;
      // Find the edges of that vertex
      for (Edge<T> edge : edges) {
        if (edge.getDestination().equals(vertex)) {
          inDegree++;
        }
        if (edge.getSource().equals(vertex)) {
          outDegree++;
        }
      }
      // Add to roots if in-degree is 0
      if (inDegree == 0 && outDegree > 0) {
        rootsList.add(vertex);
      }
      // Add the minimum element of the equivalence class
      equivalenceClass.addAll(getEquivalenceClass(vertex));
      if (!equivalenceClass.isEmpty()) {
        rootsList.add(equivalenceClass.iterator().next());
      }
    }

    roots.addAll(sortListNumerical(rootsList, false));

    return roots;
  }

  /**
   * Check if this graph is reflexive.
   *
   * @return boolean, whether this graph is reflexive.
   */
  public boolean isReflexive() {
    Boolean selfLoop;
    // Subset of edges to store all edges that have the same source
    Set<Edge<T>> subEdges;

    // Loop through the vertices
    for (T vertex : vertices) {
      // Create an empty set
      subEdges = new HashSet<Edge<T>>();

      // Find the edges of that vertex
      for (Edge<T> edge : edges) {
        if (edge.getSource().equals(vertex)) {
          subEdges.add(edge);
        }
      }

      // Go through the subset to check for self loops
      selfLoop = false;
      for (Edge<T> edge : subEdges) {
        if (edge.isSelfLoop()) {
          selfLoop = true;
          break;
        }
      }
      if (selfLoop == false) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check if this graph is symmetric.
   *
   * @return boolean, whether this graph is symmetric.
   */
  public boolean isSymmetric() {
    Boolean symmetric;
    // Loop through the edges
    for (Edge<T> edge1 : edges) {
      symmetric = false;

      // Loop again to find the other edge
      for (Edge<T> edge2 : edges) {
        if (edge2.getDestination().equals(edge1.getSource())
            && edge2.getSource().equals(edge1.getDestination())) {
          symmetric = true;
          break;
        }
      }
      if (symmetric == false) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check if this graph is transitive.
   *
   * @return boolean, whether this graph is transitive.
   */
  public boolean isTransitive() {
    Boolean transitive;
    Boolean hasEdge2;
    // Find the first edge
    for (Edge<T> edge1 : edges) {
      // Find the second edge
      for (Edge<T> edge2 : edges) {
        if (edge2.getSource().equals(edge1.getDestination())) {
          hasEdge2 = true;
          transitive = false;
          // Find the third edge
          for (Edge<T> edge3 : edges) {
            if (edge3.getSource().equals(edge1.getSource())
                && edge3.getDestination().equals(edge2.getDestination())) {
              transitive = true;
            }
          }
          if (!transitive && hasEdge2) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Check if this graph is transitive.
   *
   * @return boolean, whether this graph is transitive.
   */
  public boolean isAntiSymmetric() {
    Boolean antiSymmetric;
    Boolean hasEdge;
    // Find the first edge
    for (Edge<T> edge1 : edges) {
      antiSymmetric = false;
      hasEdge = false;
      // Find the second edge
      for (Edge<T> edge2 : edges) {
        if (edge2.getDestination().equals(edge1.getSource())
            && edge2.getSource().equals(edge1.getDestination())) {
          hasEdge = true;
          if (edge1.isSelfLoop() && edge2.isSelfLoop()) {
            antiSymmetric = true;
          }
        }
      }
      if (hasEdge && !antiSymmetric) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check if this graph is an equavalence relation.
   *
   * @return boolean, whether this graph is equavalent.
   */
  public boolean isEquivalence() {
    return isReflexive() && isSymmetric() && isTransitive() ? true : false;
  }

  /**
   * Return the equivalent class of a given vertex.
   *
   * @param vertex the vertex to search for.
   * @return a set of the equivalence class of this vertex.
   */
  public Set<T> getEquivalenceClass(T vertex) {
    Set<T> reachableNodes = new HashSet<T>();

    // Check if the graph is an equivalence relation
    if (isEquivalence()) {
      // Loop through edges to find reachable nodes
      for (Edge<T> edge : edges) {
        if (edge.getSource().equals(vertex)) {
          reachableNodes.add(edge.getDestination());
        }
      }
    }

    return reachableNodes;
  }

  /**
   * Perform breath-first search iteratively using queue. Starting from the root, enqueue it to the
   * queue and mark it as visited. Dequeue the root, find its successors and enqueue the successors.
   * Dequeue the first element of the queue and find its successors. Repeat until the queue is
   * empty.
   *
   * @return a list of vertices in breadth-first order.
   */
  public List<T> iterativeBreadthFirstSearch() {
    // Perform IBFS using queue
    Queue<T> queue = new Queue<T>();
    List<T> visited = new ArrayList<T>();
    List<T> successors;
    T temp;

    // Start from root
    for (T root : getRoots()) {
      queue.enqueue(root);
      while (!queue.isEmpty()) {
        // Dequeue the node and make it visited
        temp = queue.dequeue();
        if (!visited.contains(temp)) {
          visited.add(temp);
        }

        successors = sortListNumerical(findSuccessors(temp, visited), false);

        // Enqueue it
        for (T successor : successors) {
          queue.enqueue(successor);
        }
      }
    }
    return visited;
  }

  /**
   * Perform breath-first search iteratively using stack. Starting from the root, push it to the
   * stack and mark it as visited. Pop the root, find its successors and push the successors in
   * reverse order. Pop the first element of the stack and find its successors. Repeat until the
   * stack is empty.
   *
   * @return a list of vertices in depth-first order.
   */
  public List<T> iterativeDepthFirstSearch() {
    // Perform IDFS using stack
    Stack<T> stack = new Stack<T>();
    List<T> visited = new ArrayList<T>();
    List<T> successors;
    T temp;

    // Start from root
    for (T root : getRoots()) {
      stack.push(root);
      while (!stack.isEmpty()) {
        // Pop the node and make it visited
        temp = stack.pop();
        if (!visited.contains(temp)) {
          visited.add(temp);
        }

        successors = sortListNumerical(findSuccessors(temp, visited), true);

        // Push it
        for (T successor : successors) {
          stack.push(successor);
        }
      }
    }

    return visited;
  }

  /**
   * Perform breath-first search recursively using queue. Same algithm with iterative BFS but
   * everytime the function only perform one iteration and calls the function again. The base case
   * is the queue is empty, then return visited.
   *
   * @return a list of vertices in breadth-first order.
   */
  public List<T> recursiveBreadthFirstSearch() {
    // Perform RBFS using queue
    Queue<T> queue = new Queue<T>();
    List<T> visited = new ArrayList<T>();

    // Start from root
    for (T root : getRoots()) {
      queue.enqueue(root);

      recursiveBfs(queue, visited);
    }
    return visited;
  }

  /**
   * Perform depth-first search recursively using stack. Same algithm with iterative DFS but
   * everytime the function only perform one iteration and calls the function again. The base case
   * is the stack is empty, then return visited.
   *
   * @return a list of vertices in depth-first order.
   */
  public List<T> recursiveDepthFirstSearch() {
    // Perform RDFS using stack
    Stack<T> stack = new Stack<T>();
    List<T> visited = new ArrayList<T>();

    // Start from root
    for (T root : getRoots()) {
      stack.push(root);

      recursiveDfs(stack, visited);
    }
    return visited;
  }

  private List<T> findSuccessors(T node, List<T> visited) {
    List<T> successors = new ArrayList<T>();
    // Condition 1: the source of edge is this node
    // Condition 2: visited does not contain the successor of this node
    // Condition 3: this edge is not a self loop (for optimisation)
    for (Edge<T> edge : edges) {
      if (edge.getSource().equals(node.toString())
          && !listContains(visited, edge.getDestination())
          && !edge.isSelfLoop()) {
        successors.add(edge.getDestination());
      }
    }
    return successors;
  }

  private void recursiveBfs(Queue<T> queue, List<T> visited) {
    List<T> successors;
    T temp;

    // Base case
    if (queue.isEmpty()) {
      return;
    }

    // Dequeue the node and make it visited
    temp = queue.dequeue();
    if (!visited.contains(temp)) {
      visited.add(temp);
    }

    successors = sortListNumerical(findSuccessors(temp, visited), false);

    // Enqueue it
    for (T successor : successors) {
      queue.enqueue(successor);
    }

    // Call the function recursively
    recursiveBfs(queue, visited);
  }

  private void recursiveDfs(Stack<T> stack, List<T> visited) {
    List<T> successors;
    T temp;

    // Base case
    if (stack.isEmpty()) {
      return;
    }

    // Dequeue the node and make it visited
    temp = stack.pop();
    if (!visited.contains(temp)) {
      visited.add(temp);
    }

    successors = sortListNumerical(findSuccessors(temp, visited), true);

    // Enqueue it
    for (T successor : successors) {
      stack.push(successor);
    }

    // Call the function recursively
    recursiveDfs(stack, visited);
  }

  private Boolean listContains(List<T> list, T element) {
    for (T i : list) {
      if (i.toString().equals(element.toString())) {
        return true;
      }
    }
    return false;
  }

  private List<T> sortListNumerical(List<T> list, Boolean reverseOrder) {
    List<Integer> tempList = new ArrayList<Integer>();
    List<T> sortedList = new ArrayList<T>();

    // Convert to int and put into tempList
    for (T element : list) {
      tempList.add(Integer.parseInt(element.toString()));
    }

    if (reverseOrder) {
      Collections.sort(tempList, Collections.reverseOrder());
    } else {
      Collections.sort(tempList);
    }

    // Convert back to T
    for (int element : tempList) {
      sortedList.add((T) (Object) element);
    }

    return sortedList;
  }
}
