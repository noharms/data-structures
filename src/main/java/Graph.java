import java.util.*;
import java.util.stream.Collectors;

public abstract class Graph<T> {

    abstract public boolean contains(T value);

    abstract public List<T> shortestPath(T from, T to);

    abstract Set<T> allNeighbors(T value);

    abstract void addNode(T value);

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
