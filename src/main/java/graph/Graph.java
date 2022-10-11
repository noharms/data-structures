package graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Note: in general, since there is no order in a graph, we cannot allow duplicate values in it - we need something
 * to uniquely identify any given node of the graph. If a client would want to have a graph with duplicate values,
 * they would nevertheless need to put something unique to the graph, that is to distinguish the duplicate values
 * they would need to wrap all their values and decorating them with some other identifier.
 */
public abstract class Graph<T> {

    /**
     * based on the equals method of the generic type T
     */
    abstract public boolean contains(T value);

    abstract public List<T> shortestPath(T from, T to);

    abstract public Set<T> allNeighbors(T value);

    abstract public void addNode(T value);

    abstract public int size();

    void throwIfFound(T value) {
        if (contains(value)) {
            String msg = "Duplicate nodes are not allowed - %s already exists in graph".formatted(value);
            throw new IllegalArgumentException(msg);
        }
    }

    void throwIfNotFound(T value) {
        if (!contains(value)) {
            throw new IllegalArgumentException("Node %s is not found in graph".formatted(value));
        }
    }

    Set<T> unvisitedNeighbors(T current, Set<T> visited) {
        return allNeighbors(current).stream()
                                    .filter(neighbor -> !visited.contains(neighbor))
                                    .collect(Collectors.toSet());
    }

    static <U> List<U> reconstructPath(U endNode, Map<U, U> nodeToParent) {
        List<U> path = new LinkedList<>();
        path.add(endNode);
        U current = endNode;
        while (nodeToParent.get(current) != null) {
            current = nodeToParent.get(current);
            path.add(current);
        }
        Collections.reverse(path);
        return path;
    }
}
